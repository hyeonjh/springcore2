package com.sparta.springcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //스프링이 처음에 기동할떄 설정을 해놓으면 먼저읽음
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//extends - web---
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) {
// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시) -- 공식처럼 외우기 (룰이기떄문)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
    @Override //변경하므로 override.
    protected void configure(HttpSecurity http) throws Exception {
        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//    Post요청의 경우 (회원가입을 실제로 처리하는 요청) 아래 antMatchers만으로 허용이 안됨
//     Post요청이 있으면 csrf를 무시하는 처리를 해야한다!
        http.csrf()
                .ignoringAntMatchers("/user/**");

//        어떤요청이든 '인증'
        http.authorizeRequests()
// image 폴더를 login 없이 허용 - 뒤에 모든 경로를 허용해주겠다.
                .antMatchers("/images/**").permitAll()
// css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/user/**").permitAll()
// 어떤 요청이든 '인증' -로그인과 로그아웃 기능을 허용해달라.
                .anyRequest().authenticated()
//                and는 조건추가
                .and()
// 로그인 기능 허용
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error")
                .permitAll()
                .and()
// 로그아웃 기능 허용
                .logout()
                .permitAll();
    }
}



