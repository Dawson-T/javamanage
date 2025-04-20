package com.cqgs.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.UserInfo;
import com.cqgs.plus.mapper.UserInfoMapper;
import com.cqgs.plus.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo findUserByName(String uname){
        QueryWrapper<UserInfo> userInfoQueryWrapper=new QueryWrapper<>();
        userInfoQueryWrapper.eq("userName",uname);
        List<UserInfo> userInfoList=userInfoMapper.selectList(userInfoQueryWrapper);
        if(userInfoList!=null && userInfoList.size()>0)
            return userInfoList.get(0);
        return  null;
    }

    //注册用户
    public void addUserInfo(UserInfo userInfo){

        userInfoMapper.insert(userInfo);
    }

    //查询所有用户信息
    public List<UserInfo> findUsers(){

        return userInfoMapper.selectList(null);
    }

    //根据ID获取用户信息
    public UserInfo findUserById(Integer id){

        return userInfoMapper.selectById(id);
    }

    //修改用户
    public void updateUserInfo(UserInfo userInfo){

        userInfoMapper.updateById(userInfo);
    }

    // 分页查询用户信息
    public IPage<UserInfo> findUserPage(Integer page, Integer pageSize) {
        // 参数校验
        if (page == null || page < 1) {
            page = 1; // 默认第一页
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10; // 默认每页显示10条记录
        }

        // 初始化分页对象
        IPage<UserInfo> userPage = new Page<>(page, pageSize);

        // 执行分页查询
        userPage = userInfoMapper.selectPage(userPage, null);

        // 使用 System.out.println 输出日志信息
        System.out.println("分页查询用户信息完成，当前页：" + page + "，每页显示：" + pageSize);
        return userPage;
    }
}
