package GLINS_BE.GLINS.wishlist.dto;

import GLINS_BE.GLINS.review.domain.Review;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WishlistResponseDto {
    private Long wishlist_id;
    private Long place_id;
    private Long client_id;

    public WishlistResponseDto(Wishlist wishlist) {
        wishlist_id = wishlist.getWishlist_id();
        place_id = wishlist.getPlace().getId();
        client_id = wishlist.getClient().getId();
    }
}

