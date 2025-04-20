package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.util.Date;

@Getter
@TableName("book_category")
public class BookCategory {
    public Integer id;

    public String name;

    @TableField("parent_id")
    public Integer parentId;

    @TableField("sort_order")
    public Integer sortOrder;

    public  Integer status;

    public Date create_time;

    public Date update_time;
}
