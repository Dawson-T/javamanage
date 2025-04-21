package com.cqgs.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqgs.plus.entity.Reader;
import com.cqgs.plus.mapper.ReaderMapper;
import com.cqgs.plus.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public IPage<Reader> findReaders(int pageNum, int pageSize, Reader params) {
        Page<Reader> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Reader> queryWrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            if (params.getName() != null && !params.getName().isEmpty()) {
                queryWrapper.like(Reader::getName, params.getName());
            }
            if (params.getPhone() != null && !params.getPhone().isEmpty()) {
                queryWrapper.eq(Reader::getPhone, params.getPhone());
            }
            if (params.getEmail() != null && !params.getEmail().isEmpty()) {
                queryWrapper.eq(Reader::getEmail, params.getEmail());
            }
            if (params.getIdCard() != null && !params.getIdCard().isEmpty()) {
                queryWrapper.eq(Reader::getIdCard, params.getIdCard());
            }
            if (params.getGender() != null) {
                queryWrapper.eq(Reader::getGender, params.getGender());
            }
            if (params.getMembershipLevel() != null) {
                queryWrapper.eq(Reader::getMembershipLevel, params.getMembershipLevel());
            }
            if (params.getStatus() != null) {
                queryWrapper.eq(Reader::getStatus, params.getStatus());
            }
        }

        queryWrapper.orderByDesc(Reader::getUpdateTime);

        return readerMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Reader getReaderById(Integer readerId) {
        return readerMapper.selectById(readerId);
    }

    @Override
    public boolean addReader(Reader reader) {
        return readerMapper.insert(reader) > 0;
    }

    @Override
    public boolean updateReader(Reader reader) {
        return readerMapper.updateById(reader) > 0;
    }

    @Override
    public boolean deleteReaderById(Integer readerId) {
        return readerMapper.deleteById(readerId) > 0;
    }
}
