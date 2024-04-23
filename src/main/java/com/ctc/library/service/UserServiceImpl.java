package com.ctc.library.service;

import com.ctc.library.control.dto.EmailDTO;
import com.ctc.library.control.dto.UserDTO;
import com.ctc.library.dao.UserDao;
import com.ctc.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    private UserDao userDao;
    public List<User> GetAllUsers(Integer pageNum, Integer pageSize, String userName){
        return userDao.selectAllUsers(pageNum,pageSize,userName);
    }
    public Integer GetTotal(String userName){
        return userDao.selectTotal(userName);
    }
    public Integer update(UserDTO userDTO){
        //111：用户名已经存在
        //1：成功
        //0：失败
        User user = userDao.selectUserByName(userDTO);
        if(user!=null && !user.getUserId().equals(userDTO.getUserId()))
        {
            return 111;
        }
        else{
            if(userDao.selectUserByName(userDTO) == null){
                Integer flag = userDao.insertUser(userDTO);
                return flag>0?1:0;
            }
            else{
                Integer flag = userDao.updateAll(userDTO);
                return flag>0?1:0;
            }
        }
    }
    public User Login(UserDTO userDTO){
        User user = userDao.selectUserByName(userDTO);
        if(user.getPassword().equals(userDTO.getPassword())){
            return user;
        }
        else{
            return null;
        }
    }
    public User GetUser(UserDTO userDTO){
        return userDao.selectUserByName(userDTO);
    }
    public Integer resetPassword(EmailDTO emailDTO) {
        return userDao.updatePassword(emailDTO);
    }

    public Integer deleteUser(UserDTO userDTO) {
        return userDao.deleteByUserName(userDTO);
    }

    public List<User> GetUserByUnit(UserDTO userDTO) {
        return userDao.getUserByUnit(userDTO);
    }
}
