package com.sparta.springcore.dto;


import lombok.Getter;

@Getter
//정보를 가지고  다님 . 사용자가 입력한 정보를 dto로 받고 entity에 저장한다.
//dto는 글을쓰면 그양식을 받아오는 역할을 한다.
public class MemoRequestDto {
    private String title;
    private String contents;
    private String nickname;
}
