package GLINS_BE.GLINS.review.controller;

import GLINS_BE.GLINS.response.Response;
import GLINS_BE.GLINS.review.dto.ReviewRequestDto;
import GLINS_BE.GLINS.review.dto.ReviewResponseDto;
import GLINS_BE.GLINS.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     리뷰 작성
     */
    @PostMapping("/{placeId}")
    public Response<String> createReview(@PathVariable Long placeId, @RequestBody ReviewRequestDto requestDto) {
        return Response.success(reviewService.createReview(placeId, requestDto));
    }

    /**
     리뷰 아이디로 리뷰 검색
     */
    @GetMapping("/{reviewId}")
    public Response<ReviewResponseDto> getReviewById(@PathVariable Long reviewId){
        return Response.success(reviewService.getReviewById(reviewId));
    }
    
    /**
     회원 아이디로 리뷰 검색
     */
    @GetMapping("/client/{clientId}")
    public Response<List<ReviewResponseDto>> getReviewByClientId(@PathVariable Long clientId){
        return Response.success(reviewService.getReviewByClientId(clientId));
    }

    /**
     자기 자신의 리뷰 모두 검색
     */
    @GetMapping
    public Response<List<ReviewResponseDto>> getMyReview(){
        return Response.success(reviewService.getMyReview());
    }

    /**
     리뷰 삭제
     */
    @DeleteMapping("/{reviewId}")
    public Response<String> deleteReview(@PathVariable Long reviewId){
        return Response.success(reviewService.deleteReview(reviewId));
    }
}