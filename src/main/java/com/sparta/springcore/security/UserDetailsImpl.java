package com.sparta.springcore.security;

import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//    유저클래스의 유저정보를 가져옴. - UserDetailsServiceImpl에서 빼쓰기위해서 의존성주입함. (통로만들기)
//    UserDetailsServiceImpl에서 loadUserByUsername함수가 return new UserDetailsImpl(user)로 보냈기때문에 필요.
public class UserDetailsImpl implements UserDetails {
    private final User user;
    public UserDetailsImpl(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum userRole = user.getRole();
        String authority = userRole.getAuthority();
        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);
        return authorities;
    }
}
