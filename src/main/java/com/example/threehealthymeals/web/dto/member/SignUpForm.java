package com.example.threehealthymeals.web.dto.member;
import com.example.threehealthymeals.domain.member.Member;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class SignUpForm {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    private String imgUrl;
    
    public Member toEntity(String encryptedPassword){
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .password(encryptedPassword)
                .imgUrl(imgUrl)
                .build();
    }
}
