package com.kkb.controller;

import com.kkb.pojo.CrmUser;
import com.kkb.service.UserService;

import com.kkb.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(String usercode, String password, Model model, HttpSession session){
        try {
            CrmUser user = userService.userlogin(usercode, password);
            //将用户数据放入session
            session.setAttribute("user",user);
            //登录成功后 重定向到显示列表页面
            return "redirect:/customer/list";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "/login";
        }
    }
    //退出登录
    @RequestMapping("/logoutUser")
    public String logoutUser(HttpSession session){
        //清空session
        session.removeAttribute("user");
        return "/login";
    }

    //注册
    @RequestMapping("/doregist")
    public String registUser(String usercode,String password,String password2,Model model){
        /**
         * 1.用户名和密码不能为空
         * 2.用户名不能重复
         * 3.两次密码需要一致
         */
        if (!password.equals(password2)){
            model.addAttribute("error","两次密码不一致");
            return "regist";
        }
        try {
            CrmUser user=  userService.registUser(usercode,password);
            //注册成功则返回登录页面
            return "/login";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "regist";
        }
    }
}
