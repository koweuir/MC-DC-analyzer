package com.ctc.library.dao;

import com.ctc.library.control.dto.EmailDTO;
import com.ctc.library.control.dto.UserDTO;
import com.ctc.library.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserDao {
    List<User> selectAllUsers(Integer pageNum, Integer pageSize, String userName);
    Integer selectTotal(String userName);
    Integer insertUser(UserDTO userDTO);
    User selectUserByName(UserDTO userDTO);
    Integer updatePassword(EmailDTO emailDTO);
    Integer updateAll(UserDTO userDTO);

    Integer deleteByUserName(UserDTO userDTO);

    List<User> getUserByUnit(UserDTO userDTO);
}
