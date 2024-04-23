package com.ctc.library.service;

import com.ctc.library.control.dto.RoleDTO;
import com.ctc.library.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl {
    @Autowired
    RoleDao roleDao;

    public Integer addRolePair(RoleDTO roleDTO) {
        return roleDao.insert(roleDTO);
    }
}
