package com.example.threehealthymeals.service;

import com.example.threehealthymeals.config.auth.UserAccount;
import com.example.threehealthymeals.domain.member.*;
import com.example.threehealthymeals.web.dto.member.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BodyProfileRepository bodyProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(UserAccount::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public void login(Member member){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(member),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void updateProfile(Member member, MemberUpdateRequest request){
        BodyProfile profile = bodyProfileRepository.findByMemberId(member.getId());
        if(profile == null){
            profile = bodyProfileRepository.save(BodyProfile.builder()
                    .member(member).build());
        }
        Gender gender = Gender.valueOf(request.getGender());
        profile.update(gender, request.getHeight(), request.getWeight(), request.getTargetWeight());
    }
}
