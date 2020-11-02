package com.example.threehealthymeals.domain.restaurant;

import com.example.threehealthymeals.domain.BaseTimeEntity;
import com.example.threehealthymeals.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    private int star;

    @NotNull
    private String content;

    @Builder
    public Review(Member member, Restaurant restaurant, int star, String content){
        this.member = member;
        this.restaurant = restaurant;
        this.star = star;
        this.content = content;
    }
}
