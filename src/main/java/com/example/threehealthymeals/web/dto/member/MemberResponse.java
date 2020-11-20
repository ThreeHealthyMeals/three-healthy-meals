package com.example.threehealthymeals.web.dto.member;

import com.example.threehealthymeals.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MemberResponse {

    private String nickname;
    private String email;
    private String imgUrl;

    public MemberResponse(Member member){
        nickname = member.getNickname();
        email = member.getEmail();
        imgUrl = member.getImgUrl();
    }
}
