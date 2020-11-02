package com.example.threehealthymeals.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN", "사용자"),
    USER("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
