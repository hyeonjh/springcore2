package com.sparta.springcore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.springcore.dto.SignupRequestDto;

import com.sparta.springcore.service.KakaoUserService;
import com.sparta.springcore.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {

        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto requestDto,Errors errors, Model model) {

        if (errors.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            } /* 회원가입 페이지로 다시 리턴 */
            return "signup";
        }
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
// authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
//     유저네임 중복확인
    @GetMapping("/user/signup/{username}/exists")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@PathVariable String username){
        System.out.println("아이디중복확인C");
        return ResponseEntity.ok(userService.checkUsernameDuplicate(username));
    }
//    //이메일 중복확인
//    @GetMapping("/user/signup/{email}/exists")
//    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
//        System.out.println("되나??");
//        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
//    }
//    @PostMapping("/user/signup/{username}")
//    public int checkUsernameDuplicate(@PathVariable String username){
//        System.out.println("아이디 중복확인 체크");
//        int result = userService.usernameCheck(username);
//        return result;
//    }





}