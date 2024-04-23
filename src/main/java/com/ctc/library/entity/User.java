package com.ctc.library.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer userId; //用户ID
    private String userName;//用户名
    private String password;//密码
    private String characterName;//角色名
    private String trueName;//真实姓名
    private String phone;//联系电话
    private String email;//邮箱
    private String address;//住址
    private String sex;//用户性别
    private Integer characterId;//角色编号
    private String isRegistration;//是否注册
    private String creatTime;//创建时间
    private String avatarPath;//头像路径
    private String accountStatus;//账号状态
    private String lastLoginTime;//上次登录时间
    private Integer unitId;//用户单位编号
    private String unitName;//用户单位名称
}
