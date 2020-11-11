package com.example.threehealthymeals.web.dto;

import com.example.threehealthymeals.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MemberCreateRequest {
    private String email;
    private String password;
    private String nickname;
    private String imgUrl;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .imgUrl(imgUrl)
                .build();
    }
}
