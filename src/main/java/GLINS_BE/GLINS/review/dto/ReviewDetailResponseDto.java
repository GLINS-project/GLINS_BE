package GLINS_BE.GLINS.review.dto;

import GLINS_BE.GLINS.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReviewDetailResponseDto {
    private Long reviewId;
    private String clientNickname;
    private String content;
    private BigDecimal rating;
    private String placeName;

    public ReviewDetailResponseDto(Review review, String placeName) {
        reviewId = review.getReviewId();
        clientNickname = review.getClient().getNickname();
        content = review.getContent();
        rating = review.getRating();
        this.placeName = placeName;
    }
}
