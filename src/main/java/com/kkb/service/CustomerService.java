package com.kkb.service;

import com.github.pagehelper.PageInfo;
import com.kkb.pojo.CrmCustomer;
import com.kkb.pojo.SearchInfo;

public interface CustomerService {
    public PageInfo<CrmCustomer> getCustomerList(SearchInfo serchInfo);

    CrmCustomer getCustomerByID(String id);

    int updateCustomer(CrmCustomer customer);

    int deleteCustomer(String id);
}
