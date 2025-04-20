package com.cqgs.plus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cqgs.plus.entity.Book;
import com.cqgs.plus.entity.UserInfo;
import com.cqgs.plus.service.UserInfoService;
import com.cqgs.plus.util.HttpResult;
import com.cqgs.plus.util.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("getlogininfo")
    public HttpResult getlogininfo(HttpSession session){
        System.out.println("获取登录用户的方法"+session.getAttribute("loginUser"));
        if(session.getAttribute("loginUser")==null)
            return HttpResult.nologinResult("未登录用户");
        UserInfo loginuser=(UserInfo)session.getAttribute("loginUser");
        return HttpResult.successResult(loginuser);
    }

    @RequestMapping(value= "login" ,method = RequestMethod.POST)
    public HttpResult login(@RequestBody UserInfo userInfo, HttpSession session){
        System.out.println("登陆方法");
        if(userInfo==null || StringUtils.isEmpty(userInfo.getUserName()))
            return HttpResult.nologinResult("用户名称不能为空");
        UserInfo loginUser=userInfoService.findUserByName(userInfo.getUserName());
        if(loginUser==null)
            return HttpResult.nologinResult("用户名称错误");
        if (!loginUser.getUserPwd().equals(userInfo.getUserPwd()))
            return HttpResult.nologinResult("用户密码错误");
        //登陆成功
        String token = JwtUtil.generateToken(
                String.valueOf(loginUser.getUserId()),
                loginUser.getUserName()
        );
        HashMap<Object, Object> result = new HashMap<>();
        result.put("user", loginUser);
        result.put("token", token);
        return HttpResult.successResult(result);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public HttpResult add(@RequestBody UserInfo userInfo){
        System.out.println("注册用户信息");
        System.out.println(userInfo);
        userInfoService.addUserInfo(userInfo);
        return HttpResult.successResult("success");
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public HttpResult list(){
        System.out.println("查询所有用户信息");
        List<UserInfo> userList = userInfoService.findUsers();
        return HttpResult.successResult(userList);
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public HttpResult getUserById(@RequestParam("userId") Integer id) {
        System.out.println("根据ID查询用户信息");
        UserInfo userInfo=userInfoService.findUserById(id);
        if (userInfo == null) {
            return HttpResult.errorResult("未找到用户信息");
        }
        return HttpResult.successResult(userInfo);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody UserInfo userInfo){
        System.out.println("修改用户信息");
        System.out.println(userInfo);
        userInfoService.updateUserInfo(userInfo);
        return HttpResult.successResult("success");
    }

    @GetMapping("/page")
    public ResponseEntity<IPage<UserInfo>> getUserPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<UserInfo> userPage = userInfoService.findUserPage(page, pageSize);
        return ResponseEntity.ok(userPage);
    }
}
