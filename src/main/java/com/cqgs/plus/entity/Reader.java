package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.math.BigDecimal;

@Setter
@Getter
@TableName("reader")
public class Reader {
    @TableId(value = "reader_id", type = IdType.AUTO)
    private Integer readerId;

    private String Name;

    private  Integer gender;
    private String phone;

    private String email;

    @TableField("id_card")
    private String idCard;

    private BigDecimal deposit;
    @TableField("membership_level")
    private  Integer membershipLevel;

    private  Integer status;
    @TableField("create_time")
    private Data createTime;
    @TableField("update_time")
    private Data updateTime;
}
