package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("bookinfo")
public class Book {
    @TableId(value = "bookid",type = IdType.AUTO)
    private Integer bookid;
    private String title;
    private String author;
    private String isbn;
    private Integer price;
    private Integer categoryid;
    private Integer status;

    private Date createtime;
    private Date updatetime;

}
