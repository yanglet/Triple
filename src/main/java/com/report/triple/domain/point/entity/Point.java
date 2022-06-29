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
public class Point extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;
    private String userId;
    private int totalPoint;

    @Builder
    public Point(String userId, int totalPoint) {
        this.userId = userId;
        this.totalPoint = totalPoint;
    }
}
