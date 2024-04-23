package com.ctc.library.control.dto;

import lombok.Data;

@Data
public class CharacterDTO {
    Integer characterId;
    String characterName;
    String creatTime;
    String characterStatus;
    String comment;
}
