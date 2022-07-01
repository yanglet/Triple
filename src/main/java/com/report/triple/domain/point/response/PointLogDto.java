package com.report.triple.domain.point.response;

import com.report.triple.domain.point.entity.PointLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointLogDto {
    private String userId;
    private String action;
    private int point;
    private String reviewId;

    public PointLogDto(PointLog pointLog) {
        this.userId = pointLog.getUserId();
        this.action = pointLog.getAction();
        this.point = pointLog.getPoint();
        this.reviewId = pointLog.getReviewId();
    }
}
