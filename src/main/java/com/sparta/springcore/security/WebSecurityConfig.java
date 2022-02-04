package com.sparta.springcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //스프링이 처음에 기동할떄 설정을 해놓으면 먼저읽음
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
//extends - web---
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean //비밀번호 암호화를 위해 빈을 등록.
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
        http.csrf().disable(); // post처리가 문제없이 실행
//                .ignoringAntMatchers("/user/**");

//        어떤요청이든 '인증'
        http.authorizeRequests()
// image 폴더를 login 없이 허용 - 뒤에 모든 경로를 허용해주겠다.
                .antMatchers("/images/**").permitAll()
// css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/user/**").permitAll()
//
                .antMatchers("/board/**").permitAll()
                //로그인 없이 허용. 템플릿에 있어야함.
                .antMatchers("/").permitAll()
// 어떤 요청이든 '인증' -로그인과 로그아웃 기능을 허용해달라.
                .anyRequest().authenticated()
//                and는 조건추가
                .and()
//  글쓰게 게시판연결
//                .memos("/memos")
// [로그인 기능]
                .formLogin()
// 로그인 View 제공 (GET /user/login)
                .loginPage("/user/login")
// 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/user/login")
// 로그인 처리 후 성공 시 URL
                .defaultSuccessUrl("/")
// 로그인 처리 후 실패 시 URL
                .failureUrl("/user/login?error")
                .permitAll()
                .and()
// [로그아웃 기능]
                .logout()
// 로그아웃 처리 URL -- get이엿는데 로그아웃처리는 post형식으로 보내야함.
//  이유는 csrf protection이 기본적으로 enable 되잇기때문에 . 뭔말이지?

                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                        // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html");
    }
}
