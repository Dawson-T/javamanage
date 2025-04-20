package com.cqgs.plus.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class BorrowRecordDTO {
    private int recordId;
    private String readerName;
    private String readerPhone;
    private String bookName;
    private Date borrowTime;
    private Date dueTime;
    private Date returnTime;
    private Integer overdueDays;
    private Integer status;
    private BigDecimal fineAmount;
    private BigDecimal depositDeduction;
}
