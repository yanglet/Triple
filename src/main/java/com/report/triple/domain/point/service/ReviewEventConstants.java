package com.report.triple.domain.point.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewEventConstants {
    public static final String REVIEW_EVENT_TYPE = "REVIEW";
    public static final String REVIEW_EVENT_ACTION_ADD = "ADD";
    public static final String REVIEW_EVENT_ACTION_MOD = "MOD";
    public static final String REVIEW_EVENT_ACTION_DELETE = "DELETE";
}
