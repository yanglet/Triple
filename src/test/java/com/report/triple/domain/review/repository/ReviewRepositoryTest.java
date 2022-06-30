package com.report.triple.domain.review.repository;

import com.report.triple.domain.review.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @AfterEach
    public void after(){
        reviewRepository.deleteById("240a0658-dc5f-4878-9381-ebb7b2667772");
    }

    @Test
    @DisplayName("ExistByPlaceId 테스트")
    public void ExistByPlaceId(){
        Review review = Review.builder()
                .id("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        reviewRepository.save(review);

        assertThat(reviewRepository.existsByPlaceId("2e4baf1c-5acb-4efb-a1af-eddada31b00f"))
                .isEqualTo(true);
    }

}