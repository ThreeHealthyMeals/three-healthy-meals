package com.example.threehealthymeals.domain.restaurant;

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
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String longitude;

    @NotNull
    private String latitude;

    @Builder
    public Restaurant(String name, String address, String phoneNumber,
                      String longitude, String latitude){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}