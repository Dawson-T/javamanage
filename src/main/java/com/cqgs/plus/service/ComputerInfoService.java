package com.cqgs.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.entity.ComputerInfo;

import java.util.List;

public interface ComputerInfoService {
    //根据ID删除单个电脑
    public void deleteComputerInfo(Integer bid);
    //多条件分页查询电脑
    public IPage<ComputerInfo> findComputerPage(Integer npage, ComputerInfo computerInfo);
    //添加电脑
    public void addComputerInfo(ComputerInfo computerInfo);
    //修改电脑
    public void updateComputerInfo(ComputerInfo computerInfo);
    //查询所有电脑
    public List<ComputerInfo> findComputers();
    //根据ID查询单个电脑
    public ComputerInfo findComputerById(Integer bid);
}
