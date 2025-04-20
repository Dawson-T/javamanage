package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.util.Date;

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

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    private Date createtime;
    private Date updatetime;

}
