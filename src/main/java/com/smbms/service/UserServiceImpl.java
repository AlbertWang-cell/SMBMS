package com.smbms.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.Exception.UserException;
import com.smbms.dao.RoleMapper;
import com.smbms.dao.UserMapper;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import com.smbms.pojo.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }
    @Override
    public User login(String userCode, String password) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(password);
        List<User> list = userMapper.selectByExample(example);


      if(list.size()<1){
            throw new UserException("用户名或密码错误！");
        }
        return list.get(0);
    }

    @Override
    public List<User> findUserList() {
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public PageInfo<UserVo> getUserList(int pageNum,int pageSize,String queryUserName, Long queryUserRole) throws Exception {
        //设置页码和页面大小
        PageHelper.startPage(pageNum,pageSize);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if(queryUserName != null && !"".equals(queryUserName)) {
            criteria.andUserNameLike("%"+queryUserName+"%");
        }
            if(queryUserRole != null && queryUserRole>0) {
                criteria.andUserRoleEqualTo(queryUserRole);
            }
        List<User> list = userMapper.selectByExample(example);
            System.out.println(list.size());
            List<UserVo> userVoList = new ArrayList<>();
            UserVo userVo;
            for(User user:list){
                userVo = new UserVo();
                BeanUtils.copyProperties(user,userVo);
                userVo.setAge(new Date().getYear()-user.getBirthday().getYear());
                Role role = roleMapper.selectByPrimaryKey(user.getUserRole());
                userVo.setRoleName(role.getRoleName());
                userVoList.add(userVo);
            }

        //把分页后的list封装到带有
        PageInfo<UserVo> userPageInfo = new PageInfo<>(userVoList);
        return userPageInfo;
    }


    @Override
    public String vPwd(String oldpassword,  HttpSession session) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserPasswordEqualTo(oldpassword);
        List<User> list = userMapper.selectByExample(example);
        User loginUser = (User)session.getAttribute("loginUser");
        if(list.get(0)!=null&&!"".equals(list.get(0))){
            return "true";
        }else if(list.get(0)==null||"".equals(list.get(0))){
            return "false";
        }else if(loginUser==null||"".equals(loginUser)){
            return "sessionerror";
        }else if(oldpassword==null||"".equals(oldpassword)){
            return "error";
        }

        return null;
    }

    public void updatePwd(String oldpassword,String newpassword) throws Exception{
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserPasswordEqualTo(oldpassword);
        List<User> list = userMapper.selectByExample(example);
        list.get(0).setUserPassword(newpassword);
        userMapper.updateByPrimaryKeySelective(list.get(0));
    }
    @Override
    public String delUserById(Long id) throws Exception{
          User user = userMapper.selectByPrimaryKey(id);
          if(user==null||("").equals(user)){
              return "notexist";
          }else {
              int a = userMapper.deleteByPrimaryKey(id);
              if (a == 0) {
                  return "false";
              } else {
                  return "true";
              }
          }
    }

    public User vCode(String userCode) throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        List<User> userList = userMapper.selectByExample(example);
            if (userList.size() == 1) {
                userList.get(0).setUserCode("exist");
                return userList.get(0);
            } else {
                User user = new User();
                user.setUserCode("notexist");
                return user;
            }

    }
    @Override
    public  void addUser(User user) throws Exception{

        userMapper.insertSelective(user);
    }
    @Override
    public UserVo findUserById(Long id) throws Exception{
        User user = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        Role role = roleMapper.selectByPrimaryKey(user.getUserRole());
        userVo.setUserRoleName(role.getRoleName());
        return userVo;
    }
    @Override
    public PageInfo<User> findUserListByPage(int pageNum,int pageSize,String queryUserName,Long queryUserRole) throws Exception{
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.selectUserByList(queryUserName,queryUserRole);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return userPageInfo;
    }
    @Override
    public void modifyUser(User user) throws Exception{

        userMapper.updateByPrimaryKeySelective(user);
    }

}
