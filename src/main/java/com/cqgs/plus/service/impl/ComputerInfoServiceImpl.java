package com.cqgs.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqgs.plus.entity.ComputerInfo;
import com.cqgs.plus.mapper.ComputerInfoMapper;
import com.cqgs.plus.service.ComputerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComputerInfoServiceImpl implements ComputerInfoService {
    @Autowired
    private ComputerInfoMapper computerInfoMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteComputerInfo(Integer bid){
        System.out.println("业务方法的具体执行");
        ComputerInfo computerInfo=computerInfoMapper.selectById(bid);
        //同时完成该信息的备份
        computerInfoMapper.deleteById(bid);
        //备份数据
//        computerInfo.setComid(null);
//        computerInfo.setComname(computerInfo.getComname()+"已经被删除了，以后就不能使用了");
//        computerInfoMapper.insert(computerInfo);
    }
    //@Transactional(readOnly = true)
    @Override
    public IPage<ComputerInfo> findComputerPage(Integer npage, ComputerInfo computerInfo){
        if(npage==null)
            npage=1;
        IPage page=new Page(npage,2);//默认两条一页

        QueryWrapper<ComputerInfo> queryWrapper = new QueryWrapper();
        //QueryWrapper用于封装查询的条件(等值判断 = eq，模糊判断 like，范围判断 ge,le)
        queryWrapper.eq(StringUtils.isNotEmpty(computerInfo.getType()) , "type",computerInfo.getType());  //类型等于XX
        queryWrapper.ge(computerInfo.getPrice()!=null,"price",computerInfo.getPrice());
        queryWrapper.eq(StringUtils.isNotEmpty(computerInfo.getComname()),"comname",computerInfo.getComname());

        //调用分页的方法
        IPage computerPage = computerInfoMapper.selectPage(page, queryWrapper);
        //添加以下的代码测试
        computerInfoMapper.deleteById(5);
        return computerPage;
    }

    //添加电脑
    public void addComputerInfo(ComputerInfo computerInfo){
        computerInfoMapper.insert(computerInfo);
    }
    //修改电脑
    public void updateComputerInfo(ComputerInfo computerInfo){
        computerInfoMapper.updateById(computerInfo);
    }
    //查询所有电脑
    public List<ComputerInfo> findComputers(){
        List<ComputerInfo> computerInfoList=computerInfoMapper.selectList(null);
        return  computerInfoList;
    }
    //根据ID查询单个电脑
    public ComputerInfo findComputerById(Integer bid){
        ComputerInfo computerInfo=computerInfoMapper.selectById(bid);
        return computerInfo;
    }
}
