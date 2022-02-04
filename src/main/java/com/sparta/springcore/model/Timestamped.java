package com.sparta.springcore.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
//timestamped를 상속한 녀석이 자동으로 생성시간과 수정시간을 컬럼으로 잡도록 도와주는 역할
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
//메모클레스가 변화하는거를 듣고있다.  / 변화가 일어났으면 자동으로 업데이트 해주겠다.
public abstract class Timestamped {
    //추상 - 직접 new 식으로 만들수 x / 다른곳에서 상속되어야 한다.

    //생성시간
    @CreatedDate
    private LocalDateTime createdAt;
    //수정시간
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}