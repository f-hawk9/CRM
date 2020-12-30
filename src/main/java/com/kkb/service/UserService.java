package com.kkb.service;

import com.kkb.pojo.CrmUser;

public interface UserService {
    CrmUser userlogin(String usercode,String password) throws Exception;

    CrmUser registUser(String usercode, String password) throws Exception;

    boolean isUserExit(String userId,String  password);
}
