package com.sparta.springcore.controller;

import com.sparta.springcore.model.Comment;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.CommentService;
import com.sparta.springcore.service.MemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


//    로그인한 사용자의 username 적용 구현
//    Controller 에서 "로그인된 회원 정보 (UserDetailsImpl)" 사용하는 방법
@Slf4j
@Controller
public class HomeController {
//    기존에는 resources - static - index.html이였다면 동적으로 페이지 만들기위해 템플릿엔진을 사용하기위해서
//    html의 파일위치를 templates로 옮김
//    로그인된 사용자 정보를 유지함.

    public final CommentService commentService;
    public final MemoService memoService;
    @Autowired
    public HomeController(CommentService commentService, MemoService memoService){
        this.commentService = commentService;
        this.memoService = memoService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails!=null){
            model.addAttribute("username", userDetails.getUsername());
            return "main";
        }else{

//            List<Comment> commentList=commentService.getCommenters(userDetails.getUser());
//            model.addAttribute("comments",commentList);


        return "main";}
    }





}
