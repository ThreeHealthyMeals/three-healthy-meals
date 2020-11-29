package com.example.threehealthymeals.domain.restaurant;

import com.example.threehealthymeals.web.dto.restaurant.RestaurantListRequest;
import com.example.threehealthymeals.web.dto.restaurant.RestaurantSimple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.threehealthymeals.domain.restaurant.QMenu.menu;
import static com.example.threehealthymeals.domain.restaurant.QRestaurant.restaurant;
import static java.lang.Double.parseDouble;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

@RequiredArgsConstructor
@Repository
public class RestaurantQueryRepository {

    private static final double range = 0.1;
    private static final int minimumPrice = 5000;
    private static final int maximumPrice = 50000;
    private final JPAQueryFactory queryFactory;

    public List<RestaurantSimple> findAll(RestaurantListRequest request){
        List<RestaurantSimple> restaurants = queryFactory.select(Projections.bean(RestaurantSimple.class,
                restaurant.id, restaurant.name, restaurant.address, restaurant.phoneNumber,
                restaurant.latitude, restaurant.longitude, menu.price))
                .from(restaurant)
                .leftJoin(menu).on(menu.restaurant.id.eq(restaurant.id)).fetchJoin()
                .where(containsKeyword(request.getKeyword()),
                        excludesKeyword(request.getNegativeKeyword()),
                        coronaSafe(request.isCoronaSafe()),
                        isInPosition(request.getLatitude(), request.getLongitude()))
                .fetch();

        return request.getMaxPrice() == 0 ? restaurants :
                restaurants.stream()
                    .filter(r -> isInRangePrice(r.getPrice(), request.getMaxPrice()))
                    .collect(toList());
    }

    private boolean isInRangePrice(int price, int requestPrice){
        return requestPrice > maximumPrice || price <= Math.max(requestPrice, minimumPrice);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return isEmpty(keyword) ? null :
                restaurant.name.containsIgnoreCase(keyword);
    }

    private BooleanExpression excludesKeyword(String keyword) {
        return isEmpty(keyword) ? null :
                restaurant.name.containsIgnoreCase(keyword).not();
    }

    private BooleanExpression coronaSafe(boolean bool){
        return !bool ? null : restaurant.coronaSafe.isTrue();
    }

    private BooleanExpression isInPosition(String latitude, String longitude){
        return isEmpty(latitude)|| isEmpty(longitude) ?
                null : requireNonNull(nearLatitude(latitude)).and(nearLongitude(longitude));
    }

    private BooleanExpression nearLatitude(String latitude){
        return isEmpty(latitude) ? null :
                restaurant.latitude.subtract(parseDouble(latitude)).abs().loe(range);
    }

    private BooleanExpression nearLongitude(String longiture){
        return isEmpty(longiture) ? null :
                restaurant.longitude.subtract(parseDouble(longiture)).abs().loe(range);
    }
}
