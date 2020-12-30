package com.kkb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkb.common.CacheUtil;
import com.kkb.dao.CrmCustomerMapper;
import com.kkb.dao.CrmDictMapper;
import com.kkb.pojo.CrmCustomer;
import com.kkb.pojo.CrmCustomerExample;
import com.kkb.pojo.CrmDict;
import com.kkb.pojo.SearchInfo;
import com.kkb.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CrmCustomerMapper customerMapper;
    @Autowired
    CrmDictMapper dictMapper;

    @Autowired
    CacheUtil cacheUtil;

    @Override
    public PageInfo<CrmCustomer> getCustomerList(SearchInfo searchInfo) {
        //条件查询对象
        CrmCustomerExample example = new CrmCustomerExample();
        CrmCustomerExample.Criteria criteria = example.createCriteria();


        //名字模糊匹配
        if (StringUtils.isNotEmpty(searchInfo.getCustName())){
            criteria.andCustNameLike("%"+searchInfo.getCustName()+"%");
        }
        //客户来源
        if (StringUtils.isNotEmpty(searchInfo.getCustSource())){
            criteria.andCustSourceEqualTo(searchInfo.getCustSource());
        }
        //客户行业
        if (StringUtils.isNotEmpty(searchInfo.getCustIndustry())){
            criteria.andCustIndustryEqualTo(searchInfo.getCustIndustry());
        }
        //客户级别
        if (StringUtils.isNotEmpty(searchInfo.getCustLevel())){
            criteria.andCustLevelEqualTo(searchInfo.getCustLevel());
        }

        //执行分页  只对接下来执行的第一条语句生效
        PageHelper.startPage(searchInfo.getPage(),searchInfo.getSize());
        List<CrmCustomer> customers = customerMapper.selectByExample(example);


        //查询数据库 将id替换成名称
//        convertIDToName(customers);
        //获取本次分页的相关信息,
        convertIDtoNameWithCache(customers);
        PageInfo<CrmCustomer> pageInfo = new PageInfo<>(customers);
        return pageInfo;
    }

    @Override
    public CrmCustomer getCustomerByID(String id) {
        CrmCustomer customer = customerMapper.selectByPrimaryKey(Long.valueOf(id));
        return customer;
    }

    @Override
    public int updateCustomer(CrmCustomer customer) {
        return customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public int deleteCustomer(String id) {
        return customerMapper.deleteByPrimaryKey(Long.valueOf(id));

    }

    private void convertIDToName(List<CrmCustomer> customers) {
        /*
        1.先查询出所有dict数据
        2.在遍历所有customer 通过id到dict中查找数据
         */
        List<CrmDict> dicts = dictMapper.selectByExample(null);
        for (CrmCustomer customer:customers) {
            for (CrmDict dict:dicts){
                //当客户来源id == dict的id 那就将 name 赋值给客户的cust_source
                if (customer.getCustSource().equals(dict.getDictId())){
                    customer.setCustSource(dict.getDictItemName());
                }
                if (customer.getCustIndustry().equals(dict.getDictId())){
                    customer.setCustIndustry(dict.getDictItemName());
                }
                if (customer.getCustLevel().equals(dict.getDictId())){
                    customer.setCustLevel(dict.getDictItemName());
                }
            }
        }
    }
    private void convertIDtoNameWithCache(List<CrmCustomer> customers){
        //遍历所有客户对象  拿着id去缓存中查找
        for (CrmCustomer customer:customers){
            //客户来源
            customer.setCustSource(cacheUtil.getDictNameByID(customer.getCustSource()));
            customer.setCustIndustry(cacheUtil.getDictNameByID(customer.getCustIndustry()));
            customer.setCustLevel(cacheUtil.getDictNameByID(customer.getCustLevel()));
        }
    }
}


