package GLINS_BE.GLINS.review.dto;

import java.math.BigDecimal;

public class ReviewRequestDto {
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public BigDecimal getRating() {
        return Rating;
    }

    public void setRating(BigDecimal rating) {
        Rating = rating;
    }

    private String Content;
    private BigDecimal Rating;
}
