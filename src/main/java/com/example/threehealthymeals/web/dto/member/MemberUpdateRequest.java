package com.example.threehealthymeals.web.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MemberUpdateRequest {

    private String gender;
    private double height;
    private double weight;
    private double targetWeight;
}
