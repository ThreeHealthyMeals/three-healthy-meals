package com.example.threehealthymeals.domain.restaurant;

import com.example.threehealthymeals.web.dto.RestaurantListRequest;
import com.example.threehealthymeals.web.dto.RestaurantSimple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RestaurantQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantSimple> findAll(RestaurantListRequest request){
        return null;
    }
}
