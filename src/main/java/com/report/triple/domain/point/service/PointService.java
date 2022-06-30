package com.report.triple.domain.point.service;

import com.report.triple.domain.point.request.EarningPointRequest;
import com.report.triple.domain.point.response.EarningPointResponse;

public interface PointService {
    EarningPointResponse earningPoint(EarningPointRequest earningPointRequest);

}
