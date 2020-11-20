package com.example.threehealthymeals.web;

import com.example.threehealthymeals.config.auth.UserAccount;
import com.example.threehealthymeals.domain.member.*;
import com.example.threehealthymeals.service.MemberService;
import com.example.threehealthymeals.web.dto.member.GenderResponse;
import com.example.threehealthymeals.web.dto.member.MemberResponse;
import com.example.threehealthymeals.web.dto.member.MemberUpdateRequest;
import com.example.threehealthymeals.web.dto.member.SignUpForm;
import com.example.threehealthymeals.web.validator.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final SignUpFormValidator signUpFormValidator;
    private final PasswordEncoder passwordEncoder;
    private final BodyProfileRepository bodyProfileRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }
        Member member = signUpForm.toEntity(passwordEncoder.encode(signUpForm.getPassword()));
        memberRepository.save(member);
        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserAccount user){
        if(user != null){
            Member member = memberRepository.findByNickname(user.getUsername());
            BodyProfile profile = bodyProfileRepository.findByMemberId(member.getId());
            GenderResponse gender = new GenderResponse(profile == null ? "" :
                    profile.getGender().getDescription());
            model.addAttribute("member", new MemberResponse(member, profile));
            model.addAttribute("gender", gender);
        }
        return "account/my-page";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/mypage")
    public String updateBodyProfile(@AuthenticationPrincipal UserAccount user,
                                    MemberUpdateRequest request){
        Member member = memberRepository.findByNickname(user.getUsername());
        memberService.updateProfile(member, request);
        return "redirect:/mypage";
    }
}
