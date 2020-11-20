package com.example.threehealthymeals.web.dto.member;

import com.example.threehealthymeals.domain.member.BodyProfile;
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
    private BodyProfileResponse profile;

    public MemberResponse(Member member, BodyProfile profile){
        nickname = member.getNickname();
        email = member.getEmail();
        imgUrl = member.getImgUrl();
        if(profile != null)
            this.profile = new BodyProfileResponse(profile);
    }
}
