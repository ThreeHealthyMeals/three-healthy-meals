package com.example.threehealthymeals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyProfileRepository extends JpaRepository<BodyProfile, Long> {
    BodyProfile findByMemberId(Long id);
}
