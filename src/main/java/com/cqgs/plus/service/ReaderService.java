package com.cqgs.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqgs.plus.entity.Reader;

public interface ReaderService {
    IPage<Reader> findReaders(int pageNum, int pageSize, Reader params);

    Reader getReaderById(Integer readerId);

    boolean addReader(Reader reader);

    boolean updateReader(Reader reader);

    boolean deleteReaderById(Integer readerId);
}
