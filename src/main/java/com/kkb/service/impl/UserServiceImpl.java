package com.kkb.service.impl;


import com.kkb.dao.CrmUserMapper;
import com.kkb.pojo.CrmUser;
import com.kkb.pojo.CrmUserExample;
import com.kkb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    CrmUserMapper userMapper;

    @Override
    public CrmUser userlogin(String usercode, String password) throws Exception {
        CrmUserExample example = new CrmUserExample();
        CrmUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(usercode);
        List<CrmUser> crmUsers = userMapper.selectByExample(example);
        //查询用户账号是否存在
        if (crmUsers.size()>0){
            CrmUser user = crmUsers.get(0);
            //需要先将用户传递的明文加密， 在比对加密后的结果
            if (user.getUserPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                //用户状态是否可用 1表示正常 0表示锁定
                if (user.getUserState().equals("1")){
                    return user;
                }
                throw new Exception("账号不可用");
            }
            throw new Exception("密码不正确");
        }

        throw new Exception("账号不存在");
    }

    @Override
    public CrmUser registUser(String usercode, String password) throws Exception {
        //用户名和密码不能为空
        if (!StringUtils.isEmpty(usercode)&&!StringUtils.isEmpty(password)){
            //根据usercode查询是否已经存在
            CrmUserExample example = new CrmUserExample();
            CrmUserExample.Criteria criteria = example.createCriteria();
            criteria.andUserCodeEqualTo(usercode);
            List<CrmUser> crmUsers = userMapper.selectByExample(example);
            if(crmUsers.size()==0){
                //没有找到说明不重复 则插入数据库
                CrmUser newUser = new CrmUser();
                newUser.setUserCode(usercode);
                newUser.setUserName(usercode);
                newUser.setUserPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
                newUser.setUserState("1");
                userMapper.insert(newUser);
                return newUser;
            }
            throw new Exception("用户已存在");
        }
        throw new Exception("用户名或者密码不能为空");
    }

    @Override
    public boolean isUserExit(String userId, String password) {
        CrmUserExample example = new CrmUserExample();
        CrmUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userId);
        List<CrmUser> crmUsers = userMapper.selectByExample(example);
        //查询用户账号是否存在
        if (crmUsers.size()>0) {
            return true;
        }
        return false;
    }
}
