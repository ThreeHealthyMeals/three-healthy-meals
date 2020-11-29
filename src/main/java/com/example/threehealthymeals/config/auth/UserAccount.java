package com.example.threehealthymeals.config.auth;

import com.example.threehealthymeals.domain.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class UserAccount extends User {

    private Member member;

    public UserAccount(Member member){
        super(member.getNickname(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}
