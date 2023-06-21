package GLINS_BE.GLINS.wishlist.repository;

import GLINS_BE.GLINS.wishlist.domain.Wishlist;

import java.util.List;

public interface WishlistRepository {
    public Wishlist save(Wishlist member);
    public Wishlist delete(Wishlist member);
    Wishlist findById(Long id); // Optional is clearing Null Value.
    public List<Wishlist> findByUserId(Long user_id);
}

