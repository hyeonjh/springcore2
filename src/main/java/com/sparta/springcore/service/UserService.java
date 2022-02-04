package com.sparta.springcore.service;

import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRoleEnum;
import com.sparta.springcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service

public class UserService {

    //비밀번호 암호화 - . -websecuritycofig에서 만든 bean
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    //  DI받는부분 -  final 처리된 PasswordEncoder 넣기
    // 패스워드 인터페이스만 선언해주면 bean에서 BCryptPasswordEncoder이 의존성주입을 하게됨.
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //    회원가입처리.
    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
//        String password = requestDto.getPassword(); 암호화 처리때문에 뒤로 옮김.
// 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { //존재하는지 검사 ispresent
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        //패스워드 암호화 처리. -encode함수에서 패스워드를 암호화 처리해서 db에 저장함.
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

// 사용자 ROLE 확인 -true면 관리자.
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) { //관리자로 요청하는 사람이면, 아래 if문 처리.
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

//        유저객체만들기.
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }


    /* 회원가입 시, 유효성 체크 */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;


    }

    //    유저네임,확인
    public Boolean checkUsernameDuplicate(String username) {
        System.out.println("checkUsernameDuplicate");
        return userRepository.existsByUsername(username);
    }




//
//    public Boolean checkEmailDuplicate(String email) {
//        return userRepository.existsByEmail(email);
//    }

//    public int usernameCheck(String username) {
//        int result = userRepository.usernameCheck(username);
//        return result;
//    }
}