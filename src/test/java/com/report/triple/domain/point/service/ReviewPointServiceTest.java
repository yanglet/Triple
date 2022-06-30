package com.report.triple.domain.point.service;

import com.report.triple.domain.point.repository.PointLogRepository;
import com.report.triple.domain.point.request.EarningPointRequest;
import com.report.triple.domain.point.response.EarningPointResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewPointServiceTest {
    @Autowired
    PointService pointService;
    @Autowired
    PointLogRepository pointLogRepository;

    @Test
    @DisplayName("[ADD] 리뷰 작성 이벤트시 포인트 적립")
    @Transactional
    void earningPointV1() {
        EarningPointRequest earningPointRequest = new EarningPointRequest("REVIEW",
                "ADD",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f");

        EarningPointResponse earningPointResponse = pointService.earningPoint(earningPointRequest);

        assertThat(earningPointResponse.getPoint()).isEqualTo(3);
    }

    @Test
    @DisplayName("[MOD] 리뷰 작성 이벤트시 포인트 적립")
    @Transactional
    void earningPointV2(){
        EarningPointRequest earningPointRequest1 = new EarningPointRequest(
                "REVIEW",
                "ADD",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse1 = pointService.earningPoint(earningPointRequest1);

        EarningPointRequest earningPointRequest2 = new EarningPointRequest(
                "REVIEW",
                "MOD",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                new ArrayList<>(),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse2 = pointService.earningPoint(earningPointRequest2);

        assertThat(earningPointResponse1.getPoint()).isEqualTo(3);
        assertThat(earningPointResponse2.getPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("[DELETE] 리뷰 작성 이벤트시 포인트 적립")
    @Transactional
    void earningPointV3(){
        EarningPointRequest earningPointRequest1 = new EarningPointRequest(
                "REVIEW",
                "ADD",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse1 = pointService.earningPoint(earningPointRequest1);

        EarningPointRequest earningPointRequest2 = new EarningPointRequest(
                "REVIEW",
                "DELETE",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse2 = pointService.earningPoint(earningPointRequest2);

        assertThat(earningPointResponse1.getPoint()).isEqualTo(3);
        assertThat(earningPointResponse2.getPoint()).isEqualTo(0);
    }

    @Test
    @DisplayName("리뷰 작성 이벤트가 아닌 경우")
    @Transactional
    void earningPointV4(){
        EarningPointRequest earningPointRequest1 = new EarningPointRequest(
                "COMMENT",
                "ADD",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse1 = pointService.earningPoint(earningPointRequest1);

        assertThat(earningPointResponse1.getPoint()).isEqualTo(0);
    }

    @Test
    @DisplayName("리뷰 작성 이벤트 중 ADD, MOD, DELETE의 action이 아닐 경우")
    @Transactional
    void earningPointV5(){
        EarningPointRequest earningPointRequest1 = new EarningPointRequest(
                "REVIEW",
                "UPDATE",
                "240a0658-dc5f-4878-9381-ebb7b2667772",
                "좋아요!",
                Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"),
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );

        EarningPointResponse earningPointResponse1 = pointService.earningPoint(earningPointRequest1);

        assertThat(earningPointResponse1.getPoint()).isEqualTo(0);
    }
}