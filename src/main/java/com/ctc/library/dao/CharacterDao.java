package com.ctc.library.dao;

import com.ctc.library.control.dto.CharacterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CharacterDao {
    Integer insert(CharacterDTO characterDTO);
}
