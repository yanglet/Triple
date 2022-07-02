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
    @Column(columnDefinition = "TEXT") // 댓글이 잘리지 않도록 함
    private String content;
    @ElementCollection
    @CollectionTable(
            name = "review_attached_photo_ids",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "review_id")
    )
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

    public void update(String content, List<String> attachedPhotoIds){
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
    }
}
