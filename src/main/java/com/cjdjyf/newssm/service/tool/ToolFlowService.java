package com.cjdjyf.newssm.service.tool;

import com.cjdjyf.newssm.aop.ControllerAOP;
import com.cjdjyf.newssm.base.ProcessEntity;
import com.cjdjyf.newssm.pojo.sys.SysAccount;
import com.cjdjyf.newssm.pojo.sys.SysDic;
import com.cjdjyf.newssm.pojo.sys.flow.FlowComment;
import com.cjdjyf.newssm.pojo.tool.toolFlow.ToolFlow;
import com.cjdjyf.newssm.service.sys.SysAccountService;
import com.cjdjyf.newssm.service.sys.SysDicService;
import com.cjdjyf.newssm.utils.MyUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : cjd
 * @description : 流程控制Service
 * @date : 2018/5/8 12:26
 */
@Service
public class ToolFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private ProcessEngineFactoryBean processEngine;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SysDicService sysDicService;
    @Autowired
    private SysAccountService sysAccountService;
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 移除代理人
     * @params :[processEntity, assignee]
     * @Date : 14:46 2018/5/29
     */
    public String removeAssignee(ProcessEntity processEntity, String assignee) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        //查出当前正在活动的任务的assignee
        Task task = taskService
                .createTaskQuery()
                .processInstanceId(split[split.length - 1])
                .active().singleResult();
        List<String> assigneeList = new ArrayList<>(Arrays.asList(task.getAssignee().split(",")));
        assigneeList.remove(assignee);
        taskService.setAssignee(task.getId(), StringUtils.join(assigneeList, ","));

        return String.valueOf(assigneeList.size());
    }


    /**
     * @return : void
     * @author : cjd
     * @description : 开始一条流程 绑定业务ID 因为UUID所以不加参数了
     * @params : [key, id, map]
     * @date : 12:29 2018/5/8
     */
    @Transactional()
    public String startProcessInstance(String key, String id, HashMap map) {
        ProcessInstance pi = runtimeService//管理流程实例和执行对象，也就是表示正在执行的操作
                .startProcessInstanceByKey(key, id, map);////按照流程定义的key启动流程实例

        System.out.println("流程实例ID：" + pi.getId());//流程实例ID：101
        System.out.println("流程实例ID:" + pi.getProcessDefinitionId());//myMyHelloWorld:1:4

        return pi.getId();
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 根据流程实例ID判断有无权限 设置给流程实体类
     * @params : [processEntity]
     * @date : 16:17 2018/5/8
     */
    public void hasPermission(ProcessEntity processEntity) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        //查出当前正在活动的任务的assignee
        Task task = taskService
                .createTaskQuery()
                .processInstanceId(split[split.length - 1])
                .active().singleResult();
        if (task == null || task.getAssignee() == null) {
            processEntity.setHasPermission("0");
            return;
        }
        //判断处理人是不是自己
        if (MyUtils.stringContain(MyUtils.getUser().getId(), task.getAssignee())) {
            processEntity.setHasPermission("1");
        } else {
            processEntity.setHasPermission("0");
        }
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取流程图
     * @params : [processInstanceId, response]
     * @date : 17:55 2018/5/10
     */
    public String getFlowImage(String processInstanceId, HttpServletResponse response) {
        //logger.info("[开始]-获取流程图图像");
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            if (historicProcessInstance == null) {
                //throw new BusinessException("获取流程实例ID[" + processInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                //logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    //logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

                // 已执行的线集合
                List<String> flowIds = new ArrayList<String>();
                // 获取流程走过的线 (getHighLightedFlows是下面的方法)
                flowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);

                // 获取流程图图像字符流
                ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
                //配置字体
                InputStream imageStream = pec.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 2.0);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            //logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //logger.error("【异常】-获取流程图失败！" + e.getMessage());
            //throw new BusinessException("获取流程图失败！" + e.getMessage());
        }
        return "";
    }

    /**
     * @return : java.util.List<java.lang.String>
     * @author : cjd
     * @description : 高亮经过的线
     * @params : [bpmnModel, historicActivityInstances]
     * @date : 9:15 2018/5/11
     */
    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());
            List<FlowNode> sameStartTimeNodes = new ArrayList<FlowNode>();// 用以保存后续开始时间相同的节点
            FlowNode sameActivityImpl1 = null;

            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);// 第一个节点
            HistoricActivityInstance activityImp2_;

            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                activityImp2_ = historicActivityInstances.get(k);// 后续第1个节点

                if (!activityImpl_.getActivityType().equals("userTask") || !activityImp2_.getActivityType().equals("userTask") ||
                        !df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))) //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                {
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());//找到紧跟在后面的一个节点
                    break;
                }

            }
            sameStartTimeNodes.add(sameActivityImpl1); // 将后面第一个节点放在时间相同节点的集合里
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {// 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows(); // 取出节点的所有出去的线

            for (SequenceFlow pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 添加流程批复
     * @params : [processEntity]
     * @date : 17:56 2018/5/10
     */
    @Transactional()
    public void addComment(ProcessEntity processEntity) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        //设置审核人及部门级别（用来区分什么级别的批复） 决定#部门级别#操作人#部门名
        Authentication.setAuthenticatedUserId(processEntity.getDecide() + "#" + MyUtils.getGroupLeave() + "#" + MyUtils.getUserName() + "#" + MyUtils.getGroupName());
        taskService.addComment(task.getId(), split[split.length - 1], processEntity.getComment());//comment为批注内容
    }

    /**
     * @return : void
     * @author : cjd
     * @description : 完成任务
     * @params : [processEntity]
     * @date : 9:20 2018/5/11
     */
    @Transactional()
    public void completeTask(ProcessEntity processEntity) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        taskService.complete(task.getId(), processEntity.getProperties());
        System.out.println("完成任务：" + task.getId());
    }

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.flow.FlowComment>
     * @author : cjd
     * @description : 获取流程批复
     * @params : [processEntity]
     * @date : 17:56 2018/5/10
     */
    public TreeSet<FlowComment> getHisComment(ProcessEntity processEntity) {
        TreeSet<FlowComment> historyComments = processEntity.getCommentSet();
        String[] processInstanceIds = processEntity.getProcessInstanceId().split(",");
        for (String processInstanceId : processInstanceIds) {
            //获取流程实例
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            //通过流程实例查询所有的(用户任务类型)历史任务
            List<HistoricTaskInstance> list = historyService
                    .createHistoricTaskInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricTaskInstanceStartTime().desc()
                    .list();//查询每个历史任务的批注
            for (HistoricTaskInstance historicActivityInstance : list) {
                String historyTaskId = historicActivityInstance.getId();
                List<Comment> comments = taskService.getTaskComments(historyTaskId);

                //如果有评价就添加进整体的集合中
                if (comments != null && comments.size() > 0) {
                    for (Comment comment : comments) {
                        //决定#部门级别#操作人
                        String[] split = comment.getUserId().split("#");
                        String decide = split[0];
                        String groupLevel = split[1];
                        String userName = split[2];
                        String groupName = split[3];

                        logger.info(comment.getFullMessage());

                        Date time = comment.getTime();
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String activityName = historicActivityInstance.getName();
                        List<SysDic> sysDicList = sysDicService.findAll(new SysDic("9", decide));
                        String value = sysDicList.size() == 0 ? decide : sysDicList.get(0).getValue();
                        FlowComment flowComment = new FlowComment(value, comment.getFullMessage(), df1.format(time), groupLevel, activityName, userName, groupName);
                        historyComments.add(flowComment);
                    }
                }
            }
        }
        return historyComments;
    }

    /**
     * @return : java.util.List<com.cjdjyf.newssm.pojo.sys.flow.FlowComment>
     * @author : cjd
     * @description : 获取流程批复
     * @params : [processEntity]
     * @date : 17:56 2018/5/10
     */
    public List<HistoricTaskInstance> getHisTask(ProcessEntity processEntity) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(split[split.length - 1])
                .list();
        return list;
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 转发处理人
     * @params :[processEntity, assigneeId]
     * @Date : 17:34 2018/5/29
     */
    public String forwarding(ProcessEntity processEntity, String assigneeId) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        List<String> list = new ArrayList<>(Arrays.asList(task.getAssignee().split(",")));
        int i = list.indexOf(MyUtils.getUser().getId());
        if (i != -1) {
            String[] assignees = assigneeId.split(",");
            list.set(i, assigneeId);
            taskService.setAssignee(task.getId(), StringUtils.join(list, ","));
            processEntity.setDecide("转发");
            StringBuilder name = new StringBuilder();
            for (String assignee : assignees) {
                SysAccount byId = sysAccountService.findById(assignee);
                if (name.length() > 0) {
                    name.append(",");
                }
                name.append(byId.getUserName());
            }
            processEntity.setComment("转发给" + name);
            this.addComment(processEntity);
            return "转发成功";
        } else {
            return "转发失败";
        }
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 获取当前任务名称
     * @params : [entity]
     * @date : 18:30 2018/5/10
     */
    public String getTaskName(ProcessEntity processEntity) {
        //正在活动的任务  注意并行时是有多个正在活动的
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        if (task == null) {
            //如果有两个流程 就代表两个流程结束了 返回2
            return "";
        }
        return task.getName();
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 获取当前任务处理人
     * @params :[processEntity]
     * @Date : 17:20 2018/5/28
     */
    public String getAssignee(ProcessEntity processEntity) {
        //正在活动的任务  注意并行时是有多个正在活动的
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        if (task == null) {
            return "";
        }
        String[] accountIds = task.getAssignee().split(",");
        List<String> list = new ArrayList<>();
        for (String accountId : accountIds) {
            SysAccount sysAccount = sysAccountService.findById(accountId);
            list.add(sysAccount == null ? "" : sysAccount.getUserName());
        }
        return StringUtils.join(list, ",");
    }


    /**
     * @return :java.util.List<org.activiti.engine.repository.ProcessDefinition>
     * @Author : cjd
     * @Description : 获取流程定义列表
     * @Date : 14:17 2018/5/22
     */
    public List<ToolFlow> getToolFlow() {
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                // 查询条件
                /* .processDefinitionKey("in_flow")// 按照流程定义的key*/
                // .processDefinitionId("helloworld")//按照流程定义的ID
                //按版本升序
                .orderByProcessDefinitionVersion().desc()// 排序
                // .listPage(firstResult, maxResults)
                .list();// 多个结果集

        List<ToolFlow> toolFlowArrayList = new ArrayList<>();
        HashSet<Object> set = new HashSet<>();
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                //第一次碰到的是最新版本的
                if (set.add(pd.getKey())) {
                    ToolFlow toolFlow = new ToolFlow(pd.getId(), pd.getKey(), pd.getDeploymentId(), pd.getName(), pd.getVersion() + "");
                    toolFlowArrayList.add(toolFlow);
                }
            }
        }
        return toolFlowArrayList;
    }

    /**
     * @return :java.lang.String
     * @Author : cjd
     * @Description : 流程部署
     * @params :[files]
     * @Date : 12:50 2018/5/23
     */
    public String deploy(MultipartFile[] files) {
        DeploymentBuilder deployment = repositoryService.createDeployment();
        System.out.println(files[0].getOriginalFilename());

        String[] bpmn = files[0].getOriginalFilename().split("\\.");
        if (!bpmn[bpmn.length - 1].equals("bpmn")) {
            return "请选择正确的bpmn";
        }
        String[] png = files[1].getOriginalFilename().split("\\.");
        if (!png[png.length - 1].equals("png")) {
            return "请选择正确的png";
        }


        for (MultipartFile file : files) {
            if (file.getSize() < 0) {
                return "请选择文件";
            }
            try {
                deployment.addInputStream(file.getOriginalFilename(), file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "流程定义部署成功,流程ID:" + deployment.deploy().getId();
    }

    public String del(String key) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().desc()
                .processDefinitionKey(key)
                .list();

        for (ProcessDefinition processDefinition : list) {
             /*
         * 不带级联的删除
         * 只能删除没有启动的流程，如果流程启动，就会抛出异常
         * 能级联的删除
         * 能删除启动的流程，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
         */
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);//级联删除
        }
        return "删除成功";
    }

    /**
     * @return :void
     * @Author : cjd
     * @Description : 查看当前任务的流程定义名
     * @Date : 10:13 2018/6/7
     */
    public String findProcessDifinition(ProcessEntity processEntity) {
        String[] split = processEntity.getProcessInstanceId().split(",");
        Task task = taskService.createTaskQuery().processInstanceId(split[split.length - 1]).active().singleResult();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId())
                .singleResult();
        return processDefinition.getName();
    }
}
