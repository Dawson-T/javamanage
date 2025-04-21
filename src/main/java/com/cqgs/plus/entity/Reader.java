package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("reader")
public class Reader {

    @TableId(value = "reader_id", type = IdType.AUTO)
    private Integer readerId;

    private String name; // ✅ 改为小写

    private Integer gender;
    private String phone;
    private String email;

    @TableField("id_card")
    private String idCard;

    private BigDecimal deposit;

    @TableField("membership_level")
    private Integer membershipLevel;

    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
