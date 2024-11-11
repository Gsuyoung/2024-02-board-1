package com.green.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardInsReq {
    private String title;
    private String contents;
    private String writer;


}
