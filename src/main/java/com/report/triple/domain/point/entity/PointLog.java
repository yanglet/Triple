package com.report.triple.domain.point.entity;

import com.report.triple.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "ui_idx_point_log", columnList = "userId"),
        @Index(name = "ri_idx_point_log", columnList = "reviewId")
})
public class PointLog extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointLog_id")
    private Long id;
    private String userId;
    private String action; // ADD, MOD, DELETE
    private int point;
    private String reviewId;

    @Builder
    public PointLog(String userId, String action, int point, String reviewId) {
        this.userId = userId;
        this.action = action;
        this.point = point;
        this.reviewId = reviewId;
    }
}
