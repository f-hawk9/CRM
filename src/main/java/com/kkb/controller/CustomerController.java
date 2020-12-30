package com.kkb.controller;

import com.github.pagehelper.PageInfo;
import com.kkb.pojo.CrmCustomer;
import com.kkb.pojo.CrmDict;
import com.kkb.pojo.SearchInfo;
import com.kkb.service.CustomerService;
import com.kkb.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DictService dictService;

    //配置注入信息
    @Value("${sourceType}")
    String sourType;

    @Value("${industry}")
    String industry;

    @Value("${level}")
    String level;



    @RequestMapping("/list")
    public String getCustomerList(Model model, SearchInfo searchInfo){
        System.out.println(searchInfo);
        //客户列表
        PageInfo<CrmCustomer> list = customerService.getCustomerList(searchInfo);
        model.addAttribute("customerList",list.getList());

        //总页数
        model.addAttribute("totalPage",list.getPages());

        //客户来源
        List<CrmDict> dictList = dictService.getDictList(sourType);
        model.addAttribute("fromType",dictList);

        //所属行业
        List<CrmDict> industryList = dictService.getDictList(industry);
        model.addAttribute("industryType",industryList);

        //客户级别
        List<CrmDict> levelList = dictService.getDictList(level);
        model.addAttribute("levelType",levelList);
        return "customer";
    }
    @RequestMapping("/edit")
    @ResponseBody
    public CrmCustomer getCustomerByID(String id){
        return  customerService.getCustomerByID(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateCustomer(CrmCustomer customer){
        int i=customerService.updateCustomer(customer);
        if (i>0){
            return "update success";
        }
        else {
            return "update error";
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteCustomer(String id){
        int i =customerService.deleteCustomer(id);
        if(i>0){
            return "delete success";
        }
        else {
            return "delete error";
        }
    }
}
