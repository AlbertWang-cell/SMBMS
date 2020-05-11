package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.Exception.UserException;
import com.smbms.pojo.Data;
import com.smbms.pojo.Page;
import com.smbms.pojo.User;
import com.smbms.pojo.vo.UserVo;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/jsp")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/detail/{id}")
    public String getUser(@PathVariable Long id, Model model) throws Exception{
        User user =userService.getUserById(id);
        if(user==null){
            throw new UserException("用户不存在！");
        }
      model.addAttribute("user",user);
        return "userDetail";
    }
    @RequestMapping("/user.do")
    public String queryUserList(Model model) throws Exception{
        List<User> list = userService.findUserList();
        model.addAttribute("userList",list);
        return "userlist";
    }
    @RequestMapping("/list")
    public String getUsers(Integer pageIndex,Model model,String queryUserName, Long queryUserRole) throws Exception{
        if(pageIndex==null){
            pageIndex=1;
        }
        Page userPageInfo  = userService.getUserList(pageIndex,5,queryUserName,queryUserRole);
        model.addAttribute("pageInfo",userPageInfo );
        return "userlist";
    }
    @RequestMapping("/redirectp")
    public String redirectp() throws Exception{
        return "pwdmodify";
    }
    @RequestMapping("/vpwd")
    @ResponseBody
    public Data vPwd(String oldpassword, HttpSession session) throws Exception{
        String result = userService.vPwd(oldpassword,session);
        Data data = new Data();
        data.setResult(result);
        return data;
    }

    @RequestMapping("/pwdmodify")
    public String pwdModify(String oldpassword,String newpassword) throws Exception{
        userService.updatePwd(oldpassword,newpassword);
        return "redirect:/login.jsp";
    }

    @RequestMapping("/deluser")
    public String delUser(Long uid) throws Exception{
        userService.delUserById(uid);
        return "userlist";
    }
    @RequestMapping("/redirectu")
    public String redirectu() throws Exception{
        return "useradd";
    }
    @RequestMapping("/adduser")
    public String addUser(User userForm) throws Exception{
        userService.addUser(userForm);
        return "forward:/jsp/list";
    }
    @RequestMapping("/queryuser")
    public String queryUser(Long uid,Model model) throws Exception{
        UserVo userVo = userService.findUserById(uid);
        model.addAttribute("user",userVo);
        return "userview";
    }
    @RequestMapping("/queryuserlist")
    public String queryUserList(Integer pageIndex,Model model,String queryUserName,Long queryUserRole) throws Exception{
        PageInfo<User> userPageInfo = userService.findUserListByPage(pageIndex,5,queryUserName,queryUserRole);
        model.addAttribute("pageInfo",userPageInfo);
        return "userlist";
    }
    @RequestMapping("/redirectm")
    public String redirectm(Model model,Long uid) throws Exception{
        UserVo userVo = userService.findUserById(uid);
        model.addAttribute("user",userVo);
        return "usermodify";
    }

    @RequestMapping("/modifyuser")
    public String modifyUser(User user) throws Exception{
        userService.modifyUser(user);
        return "userlist";
    }

    @RequestMapping("/vcode")
    @ResponseBody
    public User vCode(String userCode) throws Exception{
        User info = userService.vCode(userCode);
        return info;
    }
}
