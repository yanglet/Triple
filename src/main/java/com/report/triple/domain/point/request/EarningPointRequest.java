package com.report.triple.domain.point.request;

import com.report.triple.global.validation.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EarningPointRequest {
    @NotBlank
    private String type;
    @NotBlank
    private String action;
    @NotBlank
    @UUID // UUID 포맷인지 검사
    private String reviewId;
    @NotNull // 빈 문자열 가능
    private String content;
    @Valid
    @NotNull
    @UUID
    private List<String> attachedPhotoIds;
    @NotBlank
    @UUID
    private String userId;
    @NotBlank
    @UUID
    private String placeId;
}
