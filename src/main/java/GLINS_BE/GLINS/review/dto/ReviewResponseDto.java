package GLINS_BE.GLINS.review.dto;

import GLINS_BE.GLINS.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String clientNickname;
    private String content;
    private BigDecimal rating;

    public ReviewResponseDto(Review review) {
        reviewId = review.getReviewId();
        clientNickname = review.getClient().getNickname();
        content = review.getContent();
        rating = review.getRating();
    }
}
