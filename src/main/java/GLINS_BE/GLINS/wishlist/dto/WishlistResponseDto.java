package GLINS_BE.GLINS.wishlist.dto;

import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistResponseDto {
    private Long wishlist_id;
    private Long place_id;
    private String placeName;
    private double latitude;
    private double longitude;
    private String category;
    private String hashtag;
    private String wishlist_kind;
    private Long client_id;

    public WishlistResponseDto(Wishlist wishlist) {
        wishlist_id = wishlist.getWishlist_id();
        place_id = wishlist.getPlace().getId();
        placeName = wishlist.getPlace().getPlaceName();
        latitude = wishlist.getPlace().getLatitude();
        longitude = wishlist.getPlace().getLongitude();
        category = wishlist.getPlace().getCategory();
        hashtag = wishlist.getPlace().getHashtag();
        wishlist_kind = wishlist.getWishlist_kind();
        client_id = wishlist.getClient().getId();
    }

    public WishlistResponseDto(Place place) {
        place_id = place.getId();
        placeName = place.getPlaceName();
        latitude = place.getLatitude();
        longitude = place.getLongitude();
        category = place.getCategory();
        hashtag = place.getHashtag();
    }
}


