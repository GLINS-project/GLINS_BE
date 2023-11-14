package GLINS_BE.GLINS.review.repository;

import GLINS_BE.GLINS.review.domain.Review;
import GLINS_BE.GLINS.review.dto.ReviewDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long reviewId);

    List<Review> findByPlace_Id(Long id);

    @Query("SELECT new GLINS_BE.GLINS.review.dto.ReviewDetailResponseDto(r, p.placeName) FROM Review r JOIN Place p ON r.place.id = p.id WHERE r.client.id = :clientId")
    List<ReviewDetailResponseDto> findReviewsWithPlaceNameByClientId(Long clientId);

    void delete(Review review);
}

