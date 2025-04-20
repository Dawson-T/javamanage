package com.cqgs.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqgs.plus.entity.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper  extends BaseMapper<Book> {
}

