package com.example.threehealthymeals.web.dto.member;

import com.example.threehealthymeals.domain.member.BodyProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class BodyProfileResponse {

    private String gender;
    private LocalDateTime birthday;
    private double height;
    private double weight;
    private double targetWeight;

    public BodyProfileResponse(BodyProfile profile){
        gender = profile.getGender().getDescription();
        birthday = profile.getBirthday();
        height = profile.getHeight();
        weight = profile.getWeight();
        targetWeight = profile.getTargetWeight();
    }
}
