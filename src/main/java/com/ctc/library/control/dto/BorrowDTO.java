package com.ctc.library.control.dto;

import lombok.Data;

@Data
public class BorrowDTO {
    Integer borrowId;//借阅ID (PK)
    String bookId;//图书ID(FK)
    String unitName;//借出单位
    String userName;//借阅账户ID（FK）(借入人)
    String borrowTime;//借阅时间
    String returnTime;//归还时间
    String reason;//借阅理由
    String phone;//借阅人联系方式
    String borrowStatus;//借阅状态（待审核，已审核，归还中，已结束）
    String comment;//备注
}
