package com.report.triple.domain.point.repository;

import com.report.triple.domain.point.entity.PointLog;

import java.util.List;

public interface PointLogRepositoryCustom {
    List<PointLog> findByUserIdAndReviewId(String userId, String reviewId);
}
