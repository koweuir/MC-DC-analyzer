package com.ctc.library.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Email {
    private String userName;
    private String verificationCode;
    private Timestamp expiryTime;
}
