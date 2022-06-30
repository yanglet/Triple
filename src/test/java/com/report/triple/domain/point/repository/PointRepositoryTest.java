package com.report.triple.domain.point.repository;

import com.report.triple.domain.point.entity.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class PointRepositoryTest {
    @Autowired
    PointRepository pointRepository;

    @BeforeEach
    public void before(){
        Point point = Point.builder()
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .totalPoint(0)
                .build();

        pointRepository.save(point);
    }

    @AfterEach
    public void after(){
        pointRepository.deleteByUserId("3ede0ef2-92b7-4817-a5f3-0c575361f745");
    }

    @Test
    @DisplayName("findByUserId 테스트")
    public void findByUserId(){
        assertThat(pointRepository.findByUserId("3ede0ef2-92b7-4817-a5f3-0c575361f745").getTotalPoint())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("existByUserId 테스트")
    public void existByUserId(){
        assertThat(pointRepository.existsByUserId("3ede0ef2-92b7-4817-a5f3-0c575361f745"))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("deleteByUserId 테스트")
    public void deleteByUserId(){
//        pointRepository.deleteByUserId("3ede0ef2-92b7-4817-a5f3-0c575361f745");
    }
}