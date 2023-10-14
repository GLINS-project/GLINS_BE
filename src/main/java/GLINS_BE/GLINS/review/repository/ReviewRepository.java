package GLINS_BE.GLINS.review.repository;

import GLINS_BE.GLINS.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long reviewId);
    List<Review> findByClient_Id(Long id);

    void delete(Review review);

    List<Review> findByPlace_Id(Long id);
}

