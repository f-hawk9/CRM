package com.kkb.service.impl;

import com.kkb.dao.CrmDictMapper;
import com.kkb.pojo.CrmDict;
import com.kkb.pojo.CrmDictExample;
import com.kkb.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    CrmDictMapper crmDictMapper;
    @Override
    public List<CrmDict> getDictList(String code) {
        CrmDictExample crmDictExample = new CrmDictExample();
        CrmDictExample.Criteria criteria = crmDictExample.createCriteria();
        criteria.andDictTypeCodeEqualTo(code);
        return  crmDictMapper.selectByExample(crmDictExample);


    }
}
