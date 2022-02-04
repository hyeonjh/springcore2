package com.sparta.springcore.model;


import com.sparta.springcore.dto.CommentRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor // 디폴트 생성자 자동 기입
@AllArgsConstructor // 모든 필드 포함 생성자 자동 기입
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long commentId;
    @Column( nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long boardid;



    public Comment(CommentRequestDto RequestDto,Long userId){
        this.userId = userId;
        this.content= RequestDto.getContent();
        this.boardid= RequestDto.getBoardid();

    }


}


