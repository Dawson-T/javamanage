package com.cqgs.plus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.entity.ComputerInfo;
import com.cqgs.plus.service.ComputerInfoService;
import com.cqgs.plus.util.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("computer")
public class ComputerInfoController {

    @Autowired
    private ComputerInfoService computerInfoService;

    @RequestMapping("list")
    public HttpResult list(){

        System.out.println("查询所有的电脑信息");
        //int a=10/0;
        List<ComputerInfo> computerInfoList=computerInfoService.findComputers();
        return HttpResult.successResult(computerInfoList);
    }
    @RequestMapping(value="detail",method = RequestMethod.POST)
    public  HttpResult detail(Integer bid){
        System.out.println("根据ID查询单个用户："+bid);
        ComputerInfo computerInfo=computerInfoService.findComputerById(bid);
        return HttpResult.successResult(computerInfo);
    }
    @RequestMapping("add1")
    public HttpResult add1(ComputerInfo computerInfo){
        System.out.println("添加电脑信息1");
        computerInfoService.addComputerInfo(computerInfo);
        return HttpResult.successResult("success");
    }
    @RequestMapping("add2")
    public HttpResult add2(@RequestBody ComputerInfo computerInfo){
        System.out.println("添加电脑信息2");
        computerInfoService.addComputerInfo(computerInfo);
        return HttpResult.successResult("success");
    }
    @RequestMapping("update")
    public HttpResult update(@RequestBody ComputerInfo computerInfo){
        System.out.println("修改电脑信息");
        computerInfoService.updateComputerInfo(computerInfo);
        return HttpResult.successResult("success");
    }
    @RequestMapping("delete")
    public HttpResult delete(Integer bid){
        System.out.println("根据ID删除电脑："+bid);
        computerInfoService.deleteComputerInfo(bid);
        return HttpResult.successResult("success");
    }
    @RequestMapping("page")
    public HttpResult page(Integer npage,@RequestBody ComputerInfo computerInfo){
        System.out.println("调用了条件分页的方法");
        IPage<ComputerInfo> computerInfoIPage=computerInfoService.findComputerPage(npage,computerInfo);
        return HttpResult.successResult(computerInfoIPage);
    }
}
