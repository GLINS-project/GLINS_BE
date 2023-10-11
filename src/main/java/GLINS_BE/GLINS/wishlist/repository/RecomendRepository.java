package GLINS_BE.GLINS.wishlist.repository;

import GLINS_BE.GLINS.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecomendRepository extends JpaRepository<Place, Long> {
}
