package com.report.triple.domain.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @Column(name = "review_id")
    private String id;
    @Lob // 255자 이상 텍스트가 짤리는 것을 방지
    private String content;
    @ElementCollection
    private List<String> attachedPhotoIds = new ArrayList<>();
    private String userId;
    private String placeId;
    private boolean isFirst;

    @Builder
    public Review(String id, String content, List<String> attachedPhotoIds, String userId, String placeId, boolean isFirst) {
        this.id = id;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.userId = userId;
        this.placeId = placeId;
        this.isFirst = isFirst;
    }
}
