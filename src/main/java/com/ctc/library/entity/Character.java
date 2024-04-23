package com.ctc.library.entity;

import lombok.Data;

@Data
public class Character {
    Integer characterId;
    String characterName;
    String creatTime;
    String characterStatus;
    String comment;
}
