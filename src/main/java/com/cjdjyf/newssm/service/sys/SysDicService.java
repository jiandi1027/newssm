package com.cjdjyf.newssm.service.sys;

import com.cjdjyf.newssm.base.BaseService;
import com.cjdjyf.newssm.mapper.sys.SysDicMapper;
import com.cjdjyf.newssm.pojo.sys.SysDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : cjd
 * @description : 数据字典Service
 * @date : 2018/5/2 14:10
 */
@Service
public class SysDicService extends BaseService<SysDicMapper, SysDic> {
    @Autowired
    private SysDicMapper sysDicMapper;

    public String getValueByParentKeyAndKey(String parentKey, String key) {
        return sysDicMapper.getValueByParentKeyAndKey(parentKey, key);
    }
}
