package com.report.triple.domain.point.response;

import com.report.triple.domain.point.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarningPointResponse {
    private String userId;
    private int point;

    public EarningPointResponse(Point userPoint) {
        this.userId = userPoint.getUserId();
        this.point = userPoint.getTotalPoint();
    }
}
