package com.example.threehealthymeals.domain.member;

import com.example.threehealthymeals.domain.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class BodyProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private LocalDateTime birthday;

    @NotNull
    private int height;

    @NotNull
    private int weight;

    @NotNull
    private int targetWeight;

    @Builder
    public BodyProfile(Member member, Gender gender, LocalDateTime birthday,
                       int height, int weight, int targetWeight){
        this.member = member;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
    }
}
