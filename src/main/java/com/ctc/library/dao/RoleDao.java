package com.ctc.library.dao;

import com.ctc.library.control.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao {
    Integer insert(RoleDTO roleDTO);
}
