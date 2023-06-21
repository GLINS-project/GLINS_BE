package GLINS_BE.GLINS.wishlist.dto;

import lombok.Builder;
import lombok.Data;

public class WishlistResponseDto {

    @Data
    @Builder
    public static class creat {
        private String wishlist_kind;
    }
}
