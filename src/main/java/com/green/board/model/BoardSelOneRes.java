package com.green.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSelOneRes {
    private int boardId;
    private String title;
    private String contents;
    private String writer;
    private String createdAt;
}
