package GLINS_BE.GLINS.wishlist.repository;

import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByClient_Id(Long id);
    Optional<Wishlist> findById(Long id);
    void delete(Wishlist review);
}

