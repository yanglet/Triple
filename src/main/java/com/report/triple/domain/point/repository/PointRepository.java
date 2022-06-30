package com.report.triple.domain.point.repository;

import com.report.triple.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByUserId(String userId);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);
}
