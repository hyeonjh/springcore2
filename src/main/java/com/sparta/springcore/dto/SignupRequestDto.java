package com.sparta.springcore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Setter
@Getter
public class SignupRequestDto {
    @NotEmpty(message = "아이디는 반드시 입력해야합니다. ")
    @Pattern(regexp="[a-zA-Z1-9]{3,10}", message = "아이디는 영어와 숫자로 포함해서 3~10자리 이내로 입력해주세요.")
    private String username;//?! 이거는안된다.
    @NotEmpty(message = "비밀번호는 반드시 입력해야합니다. ")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,8}", message = "비밀번호는 영어와 숫자로 포함해서 4~8자리 이내로 입력해주세요.")
    private String password;

    @NotEmpty(message = "이메일은 반드시 입력해야합니다. ")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;


    @Builder
    public SignupRequestDto(String username, String password,String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }





    private boolean admin = false;
    private String adminToken = "";
}