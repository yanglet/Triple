package com.report.triple.domain.point.response;

import com.report.triple.domain.point.entity.Point;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class EarningPointResponse {
    private String userId;
    private int point;

    public static EarningPointResponse of(Point userPoint){
        EarningPointResponse earningPointResponse = new EarningPointResponse();
        earningPointResponse.setUserId(userPoint.getUserId());
        earningPointResponse.setPoint(userPoint.getTotalPoint());

        return earningPointResponse;
    }
}
