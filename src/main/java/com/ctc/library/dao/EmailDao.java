package com.ctc.library.dao;

import com.ctc.library.control.dto.EmailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Mapper
public interface EmailDao {
    Integer updateVerificationCode(String userName, String code, Timestamp expiryTime);
    Timestamp selectExpiryTimeByName(String userName);
    Integer insertVerificationCode(String userName, String code, Timestamp expiryTime);

    String selectVerificationCodeByName(EmailDTO emailDTO);
}
