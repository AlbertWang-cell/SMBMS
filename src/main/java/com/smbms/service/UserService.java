package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.User;
import com.smbms.pojo.vo.UserVo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    User getUserById(Long id) throws Exception;
    User login(String userCode, String Password) throws Exception;
    List<User> findUserList();
    PageInfo<UserVo> getUserList(int pageNum, int pageSize, String queryUserName, Long queryUserRole) throws Exception;
    String vPwd(String oldpassword, HttpSession session) throws Exception;
    void updatePwd(String oldpassword,String newpassword) throws Exception;
    String delUserById(Long id) throws Exception;
    void addUser(User user) throws Exception;
    UserVo findUserById(Long id) throws Exception;
    PageInfo<User> findUserListByPage(int pageNum,int pageSize,String queryName,Long queryUserRole) throws Exception;
    void modifyUser(User user) throws Exception;
    User vCode(String userCode) throws Exception;
}
