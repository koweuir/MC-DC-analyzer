package com.ctc.library.control;

import cn.hutool.core.util.StrUtil;
import com.ctc.library.control.dto.CharacterDTO;
import com.ctc.library.control.dto.EmailDTO;
import com.ctc.library.control.dto.RoleDTO;
import com.ctc.library.control.dto.UserDTO;
import com.ctc.library.entity.User;
import com.ctc.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserControl {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    CharacterServiceImpl characterService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UnitServiceImpl unitService;
    @GetMapping("/GetUserList")
    public HashMap<String, Object> GetUserList(@RequestParam Integer pageNum,
                                               @RequestParam Integer pageSize,
                                               @RequestParam(defaultValue = "") String userName){
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userService.GetAllUsers(pageNum,pageSize,userName);
        Integer total = userService.GetTotal(userName);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }
    @PostMapping("/Register")
    public Integer Register(@RequestBody UserDTO userDTO){
        //0:注册失败
        //1:注册成功
        //2:用户名已经存在
        if (StrUtil.isBlank(userDTO.getUserName()) || StrUtil.isBlank(userDTO.getPassword())) {
            return 0;
        }
        else{
            User user = userService.GetUser(userDTO);
            if(user!=null){
                return 2;
            }
            //更新character表
            CharacterDTO characterDTO = new CharacterDTO();
            characterDTO.setCharacterName("读者");
            Integer CharacterId = characterService.addCharacter(characterDTO);
            //更新用户表
            userDTO.setCharacterName("读者");
            Integer flag1 = userService.update(userDTO);
            //更新role表
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setUserId(userService.GetUser(userDTO).getUserId());
            roleDTO.setCharacterId(CharacterId);
            Integer flag2 = roleService.addRolePair(roleDTO);
            //判断是否更新和插入成功
            if(flag1>0 || flag2 >0){

                return 1;
            }
            else {
                return 0;
            }
        }
    }
    @PostMapping("/Login")
    public User Login(@RequestBody UserDTO userDTO){
        if (StrUtil.isBlank(userDTO.getUserName()) || StrUtil.isBlank(userDTO.getPassword())) {
            return null;
        }
        else{
            return userService.Login(userDTO);
        }
    }
    @PostMapping("/Reset")
    public Integer Reset(@RequestBody EmailDTO emailDTO){
        //0：重置失败：用户名密码验证码其中一项空数据
        //1：重置成功
        //2：重置失败：验证码错误
        if (StrUtil.isBlank(emailDTO.getVerificationCode()) || StrUtil.isBlank(emailDTO.getPassword()) || StrUtil.isBlank(emailDTO.getUserName())) {
            return 0;
        }
        else{
            if(emailService.equalsVerificationCode(emailDTO)){
                return userService.resetPassword(emailDTO);
            }
            else {
                return 2;
            }
        }
    }
    @PostMapping("/Update")
    public User Update(@RequestBody UserDTO userDTO){
        if (StrUtil.isBlank(userDTO.getUserName()) || StrUtil.isBlank(userDTO.getPassword())) {
            return null;
        }
        else{
            if(userService.update(userDTO) == 1){
                return userService.GetUser(userDTO);
            }else if(userService.update(userDTO) == 111){
                User user =new User();
                user.setUserName("#@重名#@");
                return user;
            }
            else{
                return null;
            }
        }
    }
    @PostMapping("/Upload")
    public String Upload(@RequestParam MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "E:/学习文件夹/Java Web技术/课程设计/课设/library/vue/src/assets/user/"+fileName;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile); // 将上传的文件保存到目标文件中
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "http://localhost:9090/image/" + fileName;
    }
    @PostMapping("/Delete")
    public Integer Delete(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }
    @PostMapping("/AddUser")
    public Integer AddUser(@RequestBody UserDTO userDTO){
        //0:添加失败
        //1:添加成功
        //2:用户名已经存在
        if (StrUtil.isBlank(userDTO.getUserName()) || StrUtil.isBlank(userDTO.getPassword())) {
            return 0;
        }
        else{
            User user = userService.GetUser(userDTO);
            if(user!=null){
                return 2;
            }
            //更新character表
            CharacterDTO characterDTO = new CharacterDTO();
            characterDTO.setCharacterName(userDTO.getCharacterName());
            Integer CharacterId = null;
            if(characterService.addCharacter(characterDTO)>0)
            {
                CharacterId = characterDTO.getCharacterId();
            }
            //更新用户表
            userDTO.setCharacterId(CharacterId);
            Integer flag1 = userService.update(userDTO);
            //更新role表
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setUserId(userService.GetUser(userDTO).getUserId());
            roleDTO.setCharacterId(CharacterId);
            Integer flag2 = roleService.addRolePair(roleDTO);
            //判断是否更新和插入成功
            if(flag1>0 || flag2 >0){

                return 1;
            }
            else {
                return 0;
            }
        }
    }
    @PostMapping("/GetUserByUnit")
    public List<User> GetUserByUnit(@RequestBody UserDTO userDTO){
        return userService.GetUserByUnit(userDTO);
    }
}
