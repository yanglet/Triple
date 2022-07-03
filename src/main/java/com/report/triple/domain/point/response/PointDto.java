package com.report.triple.domain.point.response;

import com.report.triple.domain.point.entity.Point;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class PointDto {
    private String userId;
    private int totalPoint;

    public static PointDto of(Point point){
        PointDto pointDto = new PointDto();
        pointDto.setUserId(point.getUserId());
        pointDto.setTotalPoint(point.getTotalPoint());

        return pointDto;
    }
}