package com.smbms.controller;

import com.smbms.pojo.Data;
import com.smbms.pojo.User;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public String doLogin(String userCode, String userPassword, Model model, HttpSession session)throws Exception{
        User user = userService.login(userCode,userPassword);
        if(user==null){
            model.addAttribute("error","用户名或密码错误！");
            return "forward:login.jsp";
        }
        session.setAttribute("loginUser",user);
        return "frame";
    }
    @RequestMapping("/logout.do")
    public String doLogout(HttpSession session)throws Exception{
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }

}
