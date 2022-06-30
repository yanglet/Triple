package com.report.triple.domain.point.request;

import com.report.triple.global.validation.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarningPointRequest {
    @NotEmpty
    private String type;
    @NotEmpty
    private String action;
    @NotEmpty
    @UUID // UUID 포맷인지 검사
    private String reviewId;
    @NotNull // 빈 문자열 가능
    private String content;
    @NotNull
    @Valid
    private List<String> attachedPhotoIds;
    @NotEmpty
    @UUID
    private String userId;
    @NotEmpty
    @UUID
    private String placeId;
}
