package com.report.triple.domain.point.response;

import com.report.triple.domain.point.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointDto {
    private String userId;
    private int totalPoint;

    public PointDto(Point point) {
        this.userId = point.getUserId();
        this.totalPoint = point.getTotalPoint();
    }
}
