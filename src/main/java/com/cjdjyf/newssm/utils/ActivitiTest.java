package com.cjdjyf.newssm.utils;

import com.cjdjyf.newssm.constant.SysConstant;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : cjd
 * @description :
 * @date : 2018/5/7 21:35
 */
public class ActivitiTest {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RuntimeService runtimeService = processEngine.getRuntimeService();
    private TaskService taskService = processEngine.getTaskService();
    private RepositoryService repositoryService = processEngine.getRepositoryService();
    private HistoryService historyService = processEngine.getHistoryService();
    private ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();


    //部署流程
    @Test
    public void deployProcessDefinition() throws FileNotFoundException {
        //获取资源相对路径
        String bpmn = "upFlow.xml";
        String png = "upFlow.png";

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addInputStream(bpmn, new FileInputStream("D:/" + bpmn))
                .addInputStream(png, new FileInputStream("D:/" + png))
                .name("房产租赁上报")
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
    }

    //开启流程
    @Test
    public void startProcessInstance() {
        Map<String, Object> map = new HashMap<>();
        ProcessInstance pi = processEngine.getRuntimeService()//管理流程实例和执行对象，也就是表示正在执行的操作
                .startProcessInstanceByKey("in_flow", map);////按照流程定义的key启动流程实例
        System.out.println("流程实例ID：" + pi.getId());//流程实例ID：101
        System.out.println("流程实例ID：" + pi.getProcessInstanceId());//流程实例ID：101
        System.out.println("流程实例ID:" + pi.getProcessDefinitionId());//myMyHelloWorld:1:4
    }

    //查找流程
    @Test
    public void findPersonnelTaskList() {
        TaskQuery taskQuery = processEngine.getTaskService()//与任务相关的Service
                .createTaskQuery();
        List<Task> list = taskQuery
                .processInstanceId("515060")
                .orderByTaskCreateTime()
                .desc()
                .list();
/* String assignee = "张三";//当前任务办理人
        List<Task> tasks = taskQuery//创建一个任务查询对象
               *//* .taskAssignee(assignee)*//*
                .list();
                */
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("#####################################");
            }
        }
    }

    @Test
    public void addComment() {
        String processInstanceId = "165008";
        Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        //设置审核人
        Authentication.setAuthenticatedUserId(MyUtils.getUserName());
        taskService.addComment(task.getId(), processInstanceId, "嗯 很棒");//comment为批注内容
        HashMap<String, Object> hashMap = new HashMap<>();
        //发给同一级的部门负责人
        hashMap.put("permission", MyUtils.getGroupName() + "#" + SysConstant.ROLE_MANAGER + "#" + MyUtils.getGroupLeave());
        taskService.complete(task.getId(), hashMap);
    }


    //完成任务
    @Test
    public void completeTask() {
        String processInstanceId = "122515";
        Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("decide", 2);
        processEngine.getTaskService().complete(task.getId(), hashMap);
        System.out.println("完成任务：" + task.getId());
    }

    // 查询流程定义
    @Test
    public void findProcessDifinitionList() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                // 查询条件
                /* .processDefinitionKey("in_flow")// 按照流程定义的key*/
                // .processDefinitionId("helloworld")//按照流程定义的ID
                .orderByProcessDefinitionVersion().desc()// 排序
                // 返回结果
                // .singleResult()//返回惟一结果集
                // .count()//返回结果集数量
                // .listPage(firstResult, maxResults)
                .list();// 多个结果集

        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义的ID：" + pd.getId());
                System.out.println("流程定义的名称：" + pd.getName());
                System.out.println("流程定义的Key：" + pd.getKey());
                System.out.println("流程定义的部署ID：" + pd.getDeploymentId());
                System.out.println("流程定义的资源名称：" + pd.getResourceName());
                System.out.println("流程定义的版本：" + pd.getVersion());
                System.out.println("########################################################");
            }
        }
    }

    //删除流程定义
    @Test
    public void deleteProcessDifinition() {
        //部署对象ID
        String deploymentId = "132501";
        processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .deleteDeployment(deploymentId, true);

        System.out.println("删除成功~~~");//使用部署ID删除流程定义,true表示级联删除
    }

    //查看流程定义的资源文件
    @Test
    public void viewPng() throws IOException {
        //部署ID
        String deploymentId = "1";
        //获取的资源名称
        List<String> list = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        //获得资源名称后缀.png
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                if (name.indexOf(".png") >= 0) {//返回包含该字符串的第一个字母的索引位置
                    resourceName = name;
                }
            }
        }

        //获取输入流，输入流中存放.PNG的文件
        InputStream in = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, resourceName);

        //将获取到的文件保存到本地
        FileUtils.copyInputStreamToFile(in, new File("E:/" + resourceName));

        System.out.println("文件保存成功！");
    }

    //查看历史流程实例  - 流程实例id
    @Test
    public void queryHistoryProcessInstance() {
        String processInstanceId = "147501";
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance hi : list) {
                System.out.println(hi.getId() + "   " + hi.getStartTime() + "   " + hi.getEndTime());
            }
        }
    }

    //查询历史活动
    @Test
    public void findHisActivitiList() {
        String processInstanceId = "212501";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hai : list) {
                System.out.println(hai.getId() + "  " + hai.getActivityName());
            }
        }
    }

    //查询历史任务
    @Test
    public void findHisTaskList() {
        String processInstanceId = "212501";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        HistoricTaskInstance historicTaskInstance = list.get(list.size() - 2);
        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                System.out.println(hti.getId() + "    " + hti.getName() + "   " + hti.getClaimTime());
            }
        }
    }

    //查询历史流程变量
    @Test
    public void findHisVariablesList() {
        String processInstanceId = "147501";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId() + "    " + hvi.getVariableName() + "   " + hvi.getValue());
            }
        }
    }

    /**
     * 查询一次部署对应的流程定义文件名称和对应的输入流（inFlow.xml png）
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        String deploymentId = "297501";
        List<String> names = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        for (String name : names) {
            InputStream in = processEngine.getRepositoryService()
                    .getResourceAsStream(deploymentId, name);
            // 将文件保存到本地磁盘
            /*
             * OutputStream out = new FileOutputStream(new File("d:\\" + name));
             * byte[] b = new byte[1024]; int len = 0; while((len =
             * in.read(b))!=-1) { out.write(b, 0, len); } out.close();
             */
            FileUtils.copyInputStreamToFile(in, new File("d:\\" + name));
            in.close();
        }
    }

}
