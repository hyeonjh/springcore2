package com.sparta.springcore.dto;


import lombok.Getter;


@Getter
public class CommentRequestDto {
    private String content;
    private Long boardid;
}
