package com.cqgs.plus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@TableName("borrow_record")
public class BorrowRecord {

    @TableId("record_id")
    private int recordId;

    @TableField("reader_id")
    private Integer readerId;

    @TableField("book_id")
    private Integer bookId;

    @TableField("borrow_time")
    private Date borrowTime;

    @TableField("due_time")
    private Date dueTime;

    @TableField("return_time")
    private Date returnTime;

    @TableField("overdue_days")
    private Integer overdueDays = 0;

    @TableField("status")
    private Integer status = 1;

    @TableField("operator_id")
    private Integer operatorId;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("fine_amount")
    private BigDecimal fineAmount = BigDecimal.ZERO;

    @TableField("deposit_deduction")
    private BigDecimal depositDeduction = BigDecimal.ZERO;
}
