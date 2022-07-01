package com.report.triple.domain.review.repository;

import com.report.triple.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByPlaceId(String placeId);
    List<Review> findByPlaceId(String placeId);
}
