package GLINS_BE.GLINS.review.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.config.SecurityUtil;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import GLINS_BE.GLINS.review.domain.Review;
import GLINS_BE.GLINS.review.dto.ReviewDetailResponseDto;
import GLINS_BE.GLINS.review.dto.ReviewRequestDto;
import GLINS_BE.GLINS.review.dto.ReviewResponseDto;
import GLINS_BE.GLINS.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClientRepository clientRepository;
    private final PlaceRepository placeRepository;

    /**
     * 리뷰 작성
     */
    public String createReview(Long placeId, ReviewRequestDto requestDto) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.PLACE_NOT_FOUND, ErrorCode.PLACE_NOT_FOUND.getMessage()));
        Review createdReview = Review.builder().client(client).place(place).content(requestDto.getContent())
                .rating(requestDto.getRating()).build();
        reviewRepository.save(createdReview);
        return "리뷰 등록 완료";
    }

    /**
     * 리뷰 ID로 조회
     */
    public ReviewResponseDto getReviewById(Long reviewId) {
        return new ReviewResponseDto(validateReview(reviewId));
    }

    /**
     * ClientId로 리뷰 조회
     */
    public List<ReviewDetailResponseDto> getReviewByClientId(Long clientId) {
        return reviewRepository.findReviewsWithPlaceNameByClientId(clientId);
    }

    /**
     * 자신이 작성한 리뷰 모두 조회
     */
    public List<ReviewDetailResponseDto> getMyReview() {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        return reviewRepository.findReviewsWithPlaceNameByClientId(client.getId());
    }

    /**
     placeId를 사용하여 리뷰 조회
     */
    public List<ReviewResponseDto> getReviewByPlaceId(Long id) {
        return reviewRepository.findByPlace_Id(id).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰 삭제
     */
    public String deleteReview(Long reviewId) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Review review = validateReview(reviewId);

        // 현재 사용자가 작성한 리뷰인지 확인하는 로직
        if(client == review.getClient()){
            reviewRepository.delete(review);
        }else {
            throw new AllGlinsException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        return "리뷰 삭제 완료";
    }

    private Client validateClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
    }
    private Review validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.REVIEW_NOT_FOUND, ErrorCode.REVIEW_NOT_FOUND.getMessage()));
    }
}
