package com.ctc.library.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ctc.library.control.dto.EmailDTO;
import com.ctc.library.dao.EmailDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class EmailServiceImpl {
    @Autowired
    private EmailDao emailDao;
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    public Integer sendVerificationCode(String userName,String email) throws MessagingException {
        //0代表发送失败，1代表发送成功，2代表当前验证码未过期还可以继续使用
        //创建时间戳，为当前的邮件的发送时间
        Timestamp time = new Timestamp(System.currentTimeMillis());
        // 先查询之前的code是否存在还没过期的验证码
        Timestamp expiryTime = emailDao.selectExpiryTimeByName(userName);
        if(expiryTime!=null){
            if(time.after(expiryTime)){//当前时间晚于过期时间，已经过期了
                String code = RandomUtil.randomNumbers(4); // 随机一个 4位长度的验证码
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper=new MimeMessageHelper(message);
                helper.setSubject("【程序员ctc】重置密码验证");
                helper.setFrom(from);  // 发送人
                helper.setTo(email);
                helper.setSentDate(time);  // 发送时间
                String context="<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次忘记密码的验证码是："+
                        "<b>"  + code + "</b>"  + "，有效期2分钟。请妥善保管，切勿泄露";
                helper.setText(context, true);
                javaMailSender.send(message);
                // 发送成功之后，把验证码更新到数据库
                return emailDao.updateVerificationCode(userName, code, DateUtil.offsetMinute(time, 2).toTimestamp());
            }
            else{
                return 2;
            }
        }
        else{
            String code = RandomUtil.randomNumbers(4); // 随机一个 4位长度的验证码
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message);
            helper.setSubject("【程序员ctc】忘记密码验证");
            helper.setFrom(from);  // 发送人
            helper.setTo(email);
            helper.setSentDate(time);  // 发送时间
            String context="<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次忘记密码的验证码是："+
                    "<b>"  + code + "</b>"  + "，有效期2分钟。请妥善保管，切勿泄露";
            helper.setText(context, true);
            javaMailSender.send(message);
            // 发送成功之后，把验证码插入到数据库
            return emailDao.insertVerificationCode(userName, code, DateUtil.offsetMinute(time, 2).toTimestamp());
        }
    }

    public boolean equalsVerificationCode(EmailDTO emailDTO) {
        return emailDTO.getVerificationCode().equals(emailDao.selectVerificationCodeByName(emailDTO)) ;
    }
}
