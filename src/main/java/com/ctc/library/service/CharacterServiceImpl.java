package com.ctc.library.service;

import com.ctc.library.control.dto.CharacterDTO;
import com.ctc.library.dao.CharacterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl {
    @Autowired
    CharacterDao characterDao;
    public Integer addCharacter(CharacterDTO characterDTO) {
        return characterDao.insert(characterDTO);
    }
}
