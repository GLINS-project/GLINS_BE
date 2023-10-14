package GLINS_BE.GLINS.review.dto;

import java.math.BigDecimal;

public class ReviewRequestDto {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    private String content;
    private BigDecimal rating;
}
