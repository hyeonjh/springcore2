package com.sparta.springcore.controller;
import com.sparta.springcore.dto.CommentRequestDto;
import com.sparta.springcore.dto.MemoRequestDto;
import com.sparta.springcore.model.Comment;
import com.sparta.springcore.model.Memo;
import com.sparta.springcore.model.User;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController {


    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //  코멘트 생성\
    @PostMapping("/api/detail/")
    public Comment createMemo(@RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Long userID = user.getId();
        System.out.println("sad"+requestDto.getBoardid());
        Comment comment = commentService.createComment(requestDto, userID);
        return comment;
    }
    //  코멘트 조회
    @GetMapping("/api/detail/")
    public List<Comment> getComment() {
        return commentService.getComment();
    }

    //코멘트 삭제 - 코멘트 삭제 여기서 userid넣으면 유저권한?

    @DeleteMapping("/api/detail/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        //경로에 있는 변수 {id}를 받아옴
        System.out.println("댓글삭제");
        commentService.deleteComments(commentId);
        return commentId;
    }



}
