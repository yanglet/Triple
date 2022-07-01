package com.report.triple.domain.point.service;

import com.report.triple.domain.point.entity.Point;
import com.report.triple.domain.point.entity.PointLog;
import com.report.triple.domain.point.repository.PointLogRepository;
import com.report.triple.domain.point.repository.PointRepository;
import com.report.triple.domain.point.request.EarningPointRequest;
import com.report.triple.domain.point.response.EarningPointResponse;
import com.report.triple.domain.review.entity.Review;
import com.report.triple.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewPointService implements PointService{
    private final PointLogRepository pointLogRepository;
    private final PointRepository pointRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public EarningPointResponse earningPoint(EarningPointRequest earningPointRequest) {
        // 리뷰 작성 이벤트가 아닐경우
        if( !earningPointRequest.getType().equals("REVIEW") ){
            return new EarningPointResponse(earningPointRequest.getUserId(), 0);
        }

        if(earningPointRequest.getAction().equals("ADD")){ // 리뷰 작성
            // 동일 사용자가 동일한 장소에 리뷰를 2개 이상 추가할 수 없음
            if( isPresent(earningPointRequest) ){
                Point userPoint = getUserPoint(earningPointRequest.getUserId());
                return new EarningPointResponse(userPoint);
            }

            int curPoint = calculatePoint(earningPointRequest);
            Review review = Review.builder()
                    .id(earningPointRequest.getReviewId())
                    .content(earningPointRequest.getContent())
                    .attachedPhotoIds(earningPointRequest.getAttachedPhotoIds())
                    .userId(earningPointRequest.getUserId())
                    .placeId(earningPointRequest.getPlaceId())
                    .isFirst( !reviewRepository.existsByPlaceId(earningPointRequest.getPlaceId()) )
                    .build();
            reviewRepository.save(review);

            Point userPoint = getUserPoint(earningPointRequest.getUserId());
            userPoint.addPoint(curPoint);

            savePointLog(earningPointRequest, curPoint);

            return new EarningPointResponse(userPoint);
        }else if(earningPointRequest.getAction().equals("MOD")){ // 리뷰 수정
            Review review = reviewRepository.findById(earningPointRequest.getReviewId()).orElseThrow();
            int oldPoint = calculatePoint(review);
            review.update(earningPointRequest.getContent(), earningPointRequest.getAttachedPhotoIds());
            int curPoint = calculatePoint(review);

            Point userPoint = getUserPoint(earningPointRequest.getUserId());
            userPoint.addPoint(curPoint);
            userPoint.minusPoint(oldPoint);

            savePointLog(earningPointRequest, curPoint - oldPoint);

            return new EarningPointResponse(userPoint);
        }else if(earningPointRequest.getAction().equals("DELETE")){ // 리뷰 삭제
            // 장소에 대한 첫 리뷰가 삭제되어도 기존의 남아있던 리뷰들은 보너스 포인트를 받을 수 없음
            Review review = reviewRepository.findById(earningPointRequest.getReviewId()).orElseThrow();
            int oldPoint = calculatePoint(review);
            reviewRepository.deleteById(earningPointRequest.getReviewId());

            Point userPoint = pointRepository.findByUserId(earningPointRequest.getUserId());
            userPoint.minusPoint(oldPoint);

            savePointLog(earningPointRequest, oldPoint * -1);

            return new EarningPointResponse(userPoint);
        }

        return new EarningPointResponse(earningPointRequest.getUserId(), 0);
    }

    // 사용자가 여행지에 리뷰를 달았는지 여부 확인
    private boolean isPresent(EarningPointRequest earningPointRequest) {
        List<Review> ReviewList = reviewRepository.findByPlaceId(earningPointRequest.getPlaceId());
        boolean isPresent = ReviewList.stream().anyMatch(r -> r.getUserId().equals(earningPointRequest.getUserId()));
        return isPresent;
    }

    private void savePointLog(EarningPointRequest earningPointRequest, int point) {
        PointLog pointLog = PointLog.builder()
                .userId(earningPointRequest.getUserId())
                .action(earningPointRequest.getAction())
                .point(point)
                .reviewId(earningPointRequest.getReviewId())
                .build();

        pointLogRepository.save(pointLog);
    }

    private Point getUserPoint(String userId) {
        if( pointRepository.existsByUserId(userId) ){
            return pointRepository.findByUserId(userId);
        }else{
            return pointRepository.save(
                    Point.builder()
                    .userId(userId)
                    .totalPoint(0)
                    .build()
            );
        }
    }

    // ADD의 경우 Request에 대한 포인트 계산
    private int calculatePoint(EarningPointRequest earningPointRequest){
        int point = 0;
        // 1자 이상 텍스트 작성 +1
        if(earningPointRequest.getContent().length() > 0){
            point += 1;
        }
        // 1장 이상 사진 첨부 +1
        if(earningPointRequest.getAttachedPhotoIds().size() > 0){
            point += 1;
        }
        // 특정 장소에 첫 리뷰 작성 +1
        if( !reviewRepository.existsByPlaceId(earningPointRequest.getPlaceId()) ){
            point += 1;
        }

        return point;
    }

    // 기존 리뷰에 대한 포인트 계산
    private int calculatePoint(Review review){
        int point = 0;
        // 1자 이상 텍스트 작성 +1
        if(review.getContent().length() > 0){
            point += 1;
        }
        // 1장 이상 사진 첨부 +1
        if(review.getAttachedPhotoIds().size() > 0){
            point += 1;
        }
        // 특정 장소에 첫 리뷰 작성 +1
        if( review.isFirst() ){
            point += 1;
        }

        return point;
    }
}
