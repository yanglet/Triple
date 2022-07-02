package com.report.triple.domain.point.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.report.triple.domain.point.entity.PointLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PointLogDto {
    private String userId;
    private String action;
    private int point;
    private String reviewId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    public PointLogDto(PointLog pointLog) {
        this.userId = pointLog.getUserId();
        this.action = pointLog.getAction();
        this.point = pointLog.getPoint();
        this.reviewId = pointLog.getReviewId();
        this.createTime = pointLog.getCreateTime();
    }
}