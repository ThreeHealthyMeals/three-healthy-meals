package com.example.threehealthymeals.domain.member;

import com.example.threehealthymeals.domain.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Builder
    public Member(String email, String password, String nickname, String imgUrl){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
    }
}
