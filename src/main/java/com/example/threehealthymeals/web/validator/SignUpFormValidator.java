package com.example.threehealthymeals.web.validator;

import com.example.threehealthymeals.domain.member.MemberRepository;
import com.example.threehealthymeals.web.dto.member.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class SignUpFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;
        if(memberRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email", "invaild.email",
                    new Object[]{signUpForm.getEmail()}, "이미 사용 중인 이메일입니다.");
        }

        if(memberRepository.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname", "invaild.nickname",
                    new Object[]{signUpForm.getNickname()}, "이미 사용 중인 닉네임입니다.");
        }
    }
}
