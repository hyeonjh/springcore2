package com.sparta.springcore.controller;

import com.sparta.springcore.dto.MemoRequestDto;
import com.sparta.springcore.model.Memo;
import com.sparta.springcore.repository.MemoRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


//new 어쩌고 신경쓸필요 없다.
@RestController
public class MemoController {

   private final MemoService memoService ;
   private final MemoRepository memoRepository;


    @Autowired
    public MemoController (MemoService memoService,MemoRepository memoRepository){
        this.memoService = memoService;
        this.memoRepository = memoRepository;
    }

//  메모 생성\
    @PostMapping("/api/write")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Memo memo = memoService.createMemo(requestDto,userId);
        return memo;
    }
    //전체글 조회 -
    @GetMapping("/api/board/")
    public List<Memo> getBoard2(){
        return memoService.getAllMemos2();
    }
    // 내가쓴 글 조회
    @GetMapping("/api/myBoard/")
    public List<Memo> getBoardByUserId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return memoService.getMemosByUserId(userId);
    }
//  메모 업데이트
    @PutMapping("/api/myBoard/{boardid}")
    public Long updateMemo(@PathVariable Long boardid, @RequestBody MemoRequestDto requestDto){
        System.out.println("assa");
        memoService.update(boardid,requestDto);
        return boardid;
    }
    //글삭제
    @DeleteMapping("/api/myBoard/{boardid}")
    public Long deleteMemo(@PathVariable Long boardid) {
        //경로에 있는 변수 {id}를 받아옴
        System.out.println("글삭제");
        memoService.deleteMemos(boardid);
       return boardid;
    }


    @GetMapping("/api/board/{boardid}")
    public ModelAndView viewPost(@PathVariable Long boardid){
        ModelAndView model = new ModelAndView();
        Memo memo = memoRepository.findById(boardid).orElseThrow(
                () -> new NullPointerException("id가 존재하지 않습니다.")
        );
        model.addObject("memo",memo);
        model.setViewName("detail");
        return model;
    }


}