package com.sparta.springcore.repository;

import com.sparta.springcore.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//인터페이스는 클래스에서 멤버변수가 없는 메소드모음

//jpa리포짓토리를 상속한다. -> memo와 long을 가져다쓴다.
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();
    List<Memo> findAllByUserId(Long userId);



    //수정된 시간의 순서에 따라 모두 찾기. OrderBy :순서대로정렬   /Desc :내림차순
}
