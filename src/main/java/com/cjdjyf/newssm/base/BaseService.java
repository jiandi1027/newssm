package com.cjdjyf.newssm.base;

import com.cjdjyf.newssm.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author : cjd
 * @description : 基本Service
 * @date : 2018/4/24 11:18
 */
@Transactional(readOnly = true)
public class BaseService<D extends BaseDao<T>, T extends DataEntity<T>> {


    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 持久层对象
     */
    @Autowired(required = false)
    protected D dao;

    /**
     * 根据主键删除数据
     *
     * @param id
     */
    @Transactional()
    public int deleteById(String id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Transactional()
    public int batchDelete(List<String> ids) {
        try {
            if (ids != null && ids.size() > 0) {
                for (String id : ids) {
                    dao.deleteByPrimaryKey(id);
                }
                return ids.size();
            }
            return 0;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 插入数据
     */
    @Transactional()
    public String insert(T record) {
        record.setId(MyUtils.uuid());
        record.setDelFlag("0");
        record.setCreatePeople(MyUtils.getSysAccount());
        record.setCreateTime(new Date());
        if (dao.insert(record) > 0) {
            return "新增成功";
        }
        return "新增失败";
    }

    /**
     * 选择性的插入数据
     */
    @Transactional()
    public String insertSelective(T record) {
        record.setId(MyUtils.uuid());
        if (dao.insertSelective(record) > 0) {
            return "新增成功";
        }
        return "新增失败";
    }

    /**
     * 根据主键获取数据
     */
    public T findById(String id) {
        return dao.selectByPrimaryKey(id);
    }

    /**
     * 根据实体对象条件进行查询
     */
    public List<T> findAll(T record) {
        return dao.selectAll(record);
    }

    /**
     * 根据主键选择性的更新数据
     */
    @Transactional(readOnly = false)
    public String updateByIdSelective(T record) {
        record.setUpdatePeople(MyUtils.getSysAccount());
        record.setUpdateTime(new Date());
        if (dao.updateByPrimaryKeySelective(record) > 0) {
            return "修改成功";
        }
        return "修改失败";
    }

    /**
     * 根据主键更新数据
     */
    @Transactional()
    public String updateById(T record) {
        record.setUpdatePeople(MyUtils.getSysAccount());
        record.setUpdateTime(new Date());
        if (dao.updateByPrimaryKey(record) > 0) {
            return "更新成功";
        }
        return "更新失败";
    }

    /**
     * 根据主键删除数据
     */
    @Transactional()
    public String del(T record) {
        record.setDelFlag("1");
        record.setUpdatePeople(MyUtils.getSysAccount());
        record.setUpdateTime(new Date());
        if (dao.updateByPrimaryKeySelective(record) > 0) {
            return "删除成功";
        }
        return "删除失败";
    }


    /**
     * @return : com.cjdjyf.newssm.base.PageBean<T>
     * @author : cjd
     * @description : 返回分页Bean
     * @params : [t]
     * @date : 15:48 2018/4/24
     */
    @Transactional()
    public PageBean<T> findPageBean(T t) {
        PageBean<T> pageBean = new PageBean<>();
        /*分页查询*/
        if (t.getPage() != null) {
            PageHelper.startPage(t.getPage(), t.getRows());
        }
        List<T> sysRoleList = this.findAll(t);
        pageBean.setRows(sysRoleList);
        /*获取分页总数*/
        PageInfo<T> pageInfo = new PageInfo<>(sysRoleList);
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    /**
     * @return : java.lang.String
     * @author : cjd
     * @description : 编辑
     * @params : [t]
     * @date : 16:10 2018/4/24
     */
    @Transactional()
    public String save(T t) {
        if (StringUtils.isNotEmpty(t.getId())) {
            return this.updateByIdSelective(t);      //修改
        } else {
            return this.insert(t);                   //新增
        }
    }
}
