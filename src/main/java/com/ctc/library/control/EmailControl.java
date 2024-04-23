package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import com.ctc.library.control.dto.UserDTO;
import com.ctc.library.service.EmailServiceImpl;
import com.ctc.library.service.UserServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Email")
public class EmailControl {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserServiceImpl userService;
    @GetMapping("/SendVerificationCode")
    public Integer SendVerificationCode(@RequestParam String userName, String email) throws MessagingException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        if(StrUtil.isBlank(email) || userName == null) {
            return 0;
        }
        else if(userService.GetUser(userDTO) == null){
            return 0;
        }
        else{
            return emailService.sendVerificationCode(userName, email);
        }
    }
}
