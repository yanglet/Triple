package com.report.triple.domain.point.repository;

import com.report.triple.domain.point.entity.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog, Long>, PointLogRepositoryCustom {
}
