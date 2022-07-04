package com.report.triple.domain.point.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.report.triple.domain.point.entity.PointLog;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.report.triple.domain.point.entity.QPointLog.pointLog;

@RequiredArgsConstructor
public class PointLogRepositoryImpl implements PointLogRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PointLog> findByUserIdAndReviewId(String userId, String reviewId) {
        return queryFactory.selectFrom(pointLog)
                .where( allEq(userId, reviewId) )
                .fetch();
    }

    private BooleanExpression allEq(String userIdCond, String reviewIdCond){
        return userIdEq(userIdCond) != null ? userIdEq(userIdCond).and(reviewIdEq(reviewIdCond)) : reviewIdEq(reviewIdCond);
    }

    private BooleanExpression userIdEq(String userIdCond){
        return userIdCond != null ? pointLog.userId.eq(userIdCond) : null;
    }

    private BooleanExpression reviewIdEq(String reviewIdCond){
        return reviewIdCond != null ? pointLog.reviewId.eq(reviewIdCond) : null;
    }
}
