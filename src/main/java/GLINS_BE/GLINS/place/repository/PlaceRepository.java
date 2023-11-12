package GLINS_BE.GLINS.place.repository;

import GLINS_BE.GLINS.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByPlaceNameAndLatitudeAndLongitude(String placeName, double latitude, double longitude);
}
