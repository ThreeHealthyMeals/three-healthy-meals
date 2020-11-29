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
    private Gender gender = Gender.MALE;

    @NotNull
    private LocalDateTime birthday;

    @NotNull
    private double height;

    @NotNull
    private double weight;

    @NotNull
    private double targetWeight;

    @Builder
    public BodyProfile(Member member, Gender gender, LocalDateTime birthday,
                       double height, double weight, double targetWeight){
        this.member = member;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
    }

    public void update(Gender gender, double height, double weight,
                       double targetWeight){
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
    }
}
