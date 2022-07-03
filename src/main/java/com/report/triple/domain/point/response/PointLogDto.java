package com.report.triple.domain.point.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.report.triple.domain.point.entity.PointLog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class PointLogDto {
    private String userId;
    private String action;
    private int point;
    private String reviewId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    public static PointLogDto of(PointLog pointLog){
        PointLogDto pointLogDto = new PointLogDto();
        pointLogDto.setUserId(pointLog.getUserId());
        pointLogDto.setAction(pointLog.getAction());
        pointLogDto.setPoint(pointLog.getPoint());
        pointLogDto.setReviewId(pointLog.getReviewId());
        pointLogDto.setCreateTime(pointLog.getCreateTime());

        return pointLogDto;
    }
}