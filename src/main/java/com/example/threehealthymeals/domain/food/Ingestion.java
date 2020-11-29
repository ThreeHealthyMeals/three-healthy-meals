package com.example.threehealthymeals.domain.food;

import com.example.threehealthymeals.domain.BaseTimeEntity;
import com.example.threehealthymeals.domain.member.Member;
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
public class Ingestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @NotNull
    private LocalDateTime ingestDate;

    @Builder
    public Ingestion(Member member, Food food, LocalDateTime ingestDate){
        this.member = member;
        this.food = food;
        this.ingestDate = ingestDate;
    }
}
