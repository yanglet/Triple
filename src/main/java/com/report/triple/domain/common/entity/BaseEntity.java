package com.report.triple.domain.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity{
    @CreatedDate
    @Column(updatable = false) // 생성시각이 수정되지 않도록 방지
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
