package com.sparta.springcore.model;

import com.sparta.springcore.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Setter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Memo extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long boardid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long userId;






    public Memo(String username, String contents,Long userId) {
        this.userId = userId;
        this.title = title;
        this.nickname = nickname;
        this.contents = contents;
    }

    public Memo(MemoRequestDto requestDto, Long userId) {
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.nickname = requestDto.getNickname();
        this.contents = requestDto.getContents();
    }



    public void update(MemoRequestDto requestDto) {
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.nickname = requestDto.getNickname();
        this.contents = requestDto.getContents();
    }

}
