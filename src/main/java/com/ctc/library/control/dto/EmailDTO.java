package com.ctc.library.control.dto;

import lombok.Data;

@Data
public class EmailDTO {
    private String userName;
    private String verificationCode;
    private String password;
}
