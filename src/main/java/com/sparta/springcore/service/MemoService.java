package com.sparta.springcore.service;


import com.sparta.springcore.dto.MemoRequestDto;
import com.sparta.springcore.model.Memo;
import com.sparta.springcore.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;
    //꼭필요하다고 선언해야함 - requiredArgsConstructor

    @Autowired
    public MemoService (MemoRepository memoRepository){
        this.memoRepository = memoRepository;
    }

    @Transactional //db에 반영이 되도록 함
    public Long update(Long boardid, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(boardid).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                () -> new NullPointerException("게시글이 읍다.")
        );
        memo.update(requestDto);
        return memo.getBoardid();
    }

    public Memo createMemo(MemoRequestDto requestDto,Long userId) {
        Memo memo = new Memo(requestDto,userId);
        memoRepository.save(memo);
        return memo;
    }
    public List<Memo> getAllMemos2() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }
    public List<Memo> getMemosByUserId(Long userId) {
        return memoRepository.findAllByUserId(userId);
    }

    public void deleteMemos(Long boardid) {
        memoRepository.deleteById(boardid);
    }


}