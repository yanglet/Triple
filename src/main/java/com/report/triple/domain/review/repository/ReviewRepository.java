package com.report.triple.domain.review.repository;

import com.report.triple.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByPlaceId(String placeId);
}
