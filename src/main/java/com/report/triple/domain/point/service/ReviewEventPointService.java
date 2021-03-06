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

import static com.report.triple.domain.point.service.ReviewEventConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewEventPointService implements PointService{
    private final PointLogRepository pointLogRepository;
    private final PointRepository pointRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public EarningPointResponse earningPoint(EarningPointRequest earningPointRequest) {
        // 리뷰 작성 이벤트가 아닐경우
        if( !earningPointRequest.getType().equals(REVIEW_EVENT_TYPE) ){
            Point userPoint = getUserPoint(earningPointRequest.getUserId());
            return EarningPointResponse.of(userPoint);
        }

        if(earningPointRequest.getAction().equals(REVIEW_EVENT_ACTION_ADD)){ // 리뷰 작성
            // 동일 사용자가 동일한 장소에 리뷰를 2개 이상 추가할 수 없음
            if( isPresent(earningPointRequest) ){
                Point userPoint = getUserPoint(earningPointRequest.getUserId());
                return EarningPointResponse.of(userPoint);
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

            return EarningPointResponse.of(userPoint);
        }else if(earningPointRequest.getAction().equals(REVIEW_EVENT_ACTION_MOD)){ // 리뷰 수정
            Review review = reviewRepository.findById(earningPointRequest.getReviewId()).orElseThrow();
            int oldPoint = calculatePoint(review);
            // 리뷰 작성한 사용자가 아닌 경우 수정 불가능
            if( !earningPointRequest.getUserId().equals(review.getUserId()) ){
                Point userPoint = getUserPoint(earningPointRequest.getUserId());
                return EarningPointResponse.of(userPoint);
            }

            review.update(earningPointRequest.getContent(), earningPointRequest.getAttachedPhotoIds());
            int curPoint = calculatePoint(review);

            Point userPoint = getUserPoint(earningPointRequest.getUserId());
            userPoint.addPoint(curPoint);
            userPoint.minusPoint(oldPoint);

            savePointLog(earningPointRequest, curPoint - oldPoint);

            return EarningPointResponse.of(userPoint);
        }else if(earningPointRequest.getAction().equals(REVIEW_EVENT_ACTION_DELETE)){ // 리뷰 삭제
            // 장소에 대한 첫 리뷰가 삭제되어도 기존의 남아있던 리뷰들은 보너스 포인트를 받을 수 없음
            Review review = reviewRepository.findById(earningPointRequest.getReviewId()).orElseThrow();
            int oldPoint = calculatePoint(review);
            reviewRepository.deleteById(earningPointRequest.getReviewId());

            Point userPoint = getUserPoint(earningPointRequest.getUserId());
            userPoint.minusPoint(oldPoint);

            savePointLog(earningPointRequest, oldPoint * -1);

            return EarningPointResponse.of(userPoint);
        }

        Point userPoint = getUserPoint(earningPointRequest.getUserId());
        return EarningPointResponse.of(userPoint);
    }

    // 사용자가 여행지에 리뷰를 달았는지 여부 확인
    private boolean isPresent(EarningPointRequest earningPointRequest) {
        List<Review> reviewList = reviewRepository.findByPlaceId(earningPointRequest.getPlaceId());
        boolean isPresent = reviewList.stream().anyMatch(r -> r.getUserId().equals(earningPointRequest.getUserId()));
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
        if( checkAttachedPhotoIds(earningPointRequest.getAttachedPhotoIds()) ){
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
        if( checkAttachedPhotoIds(review.getAttachedPhotoIds()) ){
            point += 1;
        }
        // 특정 장소에 첫 리뷰 작성 +1
        if( review.isFirst() ){
            point += 1;
        }

        return point;
    }

    // 사진이 첨부 되었는지 확인
    private boolean checkAttachedPhotoIds(List<String> attachedPhotoIds){
        if( !attachedPhotoIds.isEmpty() ){
            return true;
        }
        return false;
    }
}
