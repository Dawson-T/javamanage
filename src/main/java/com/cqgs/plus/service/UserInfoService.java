package com.cqgs.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.ComputerInfo;
import com.cqgs.plus.entity.UserInfo;
import org.apache.catalina.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInfoService {
    UserInfo findUserByName(String uname);

    //用户注册
    public void addUserInfo(UserInfo userInfo);

    //查询所有用户
    public List<UserInfo> findUsers();

    //根据ID查询用户信息
    public UserInfo findUserById(Integer id);

    //修改用户信息
    public void updateUserInfo(UserInfo userInfo);

    //分页查询用户信息
    IPage<UserInfo> findUserPage(Integer page, Integer pageSize);
}
