package com.green.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSelRes {
    private int boardId;
    private String title;
    private String writer;
    private String createdAt;
}
