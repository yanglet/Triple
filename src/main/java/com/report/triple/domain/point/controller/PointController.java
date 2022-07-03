package com.report.triple.domain.point.controller;

import com.report.triple.domain.point.entity.Point;
import com.report.triple.domain.point.repository.PointLogRepository;
import com.report.triple.domain.point.repository.PointRepository;
import com.report.triple.domain.point.request.EarningPointRequest;
import com.report.triple.domain.point.response.EarningPointResponse;
import com.report.triple.domain.point.response.PointDto;
import com.report.triple.domain.point.response.PointLogDto;
import com.report.triple.domain.point.service.PointService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final PointRepository pointRepository;
    private final PointLogRepository pointLogRepository;

    @ApiOperation("포인트 적립")
    @PostMapping("/events")
    public ResponseEntity<EarningPointResponse> earningPoint(@RequestBody @Validated EarningPointRequest earningPointRequest){
        EarningPointResponse earningPointResponse = pointService.earningPoint(earningPointRequest);
        return ResponseEntity.ok(earningPointResponse);
    }

    @ApiOperation("전체 사용자 포인트 조회")
    @GetMapping("/points")
    public ResponseEntity<List<PointDto>> getPoints(){
        List<PointDto> result = pointRepository.findAll()
                .stream()
                .map(PointDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @ApiOperation("사용자 포인트 조회")
    @GetMapping("/points/{userId}")
    public ResponseEntity<PointDto> getPoint(@PathVariable(name = "userId") String userId){
        Point point = pointRepository.findByUserId(userId);
        return ResponseEntity.ok(PointDto.of(point));
    }

    @ApiOperation("사용자 포인트 적립 이력 조회")
    @GetMapping("/points/logs")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "userId",
                    value = "사용자 식별값",
                    dataType = "string",
                    paramType = "query"
            ),
            @ApiImplicitParam(
                    name = "reviewId",
                    value = "리뷰 식별값",
                    dataType = "string",
                    paramType = "query"
            )
    })
    public ResponseEntity<List<PointLogDto>> getPointsLogs(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "reviewId", required = false) String reviewId
    ){
        List<PointLogDto> result = pointLogRepository.findByUserIdAndReviewId(userId, reviewId)
                .stream()
                .map(PointLogDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
