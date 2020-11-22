package com.example.threehealthymeals.web.dto.member;

import com.example.threehealthymeals.domain.member.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@Setter
public class GenderResponse {
    private List<GenderSimple> option;

    public GenderResponse(String select){
        option = Arrays.stream(Gender.values())
                .map(gender -> new GenderSimple(gender, select))
                .collect(toList());
    }
}


@Getter
@NoArgsConstructor
@Setter
class GenderSimple {
    private String value;
    private String name;
    private boolean selected = false;

    public GenderSimple(Gender gender, String select){
        value = gender.name();
        name = gender.getDescription();
        selected = name.equals(select);
    }
}
