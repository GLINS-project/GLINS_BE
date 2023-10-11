package GLINS_BE.GLINS.wishlist.controller;

import GLINS_BE.GLINS.response.Response;
import GLINS_BE.GLINS.wishlist.dto.WishlistRequestDto;
import GLINS_BE.GLINS.wishlist.dto.WishlistResponseDto;
import GLINS_BE.GLINS.wishlist.service.WishlistService;
import com.nimbusds.jose.shaded.json.JSONArray;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;


    /**
     * 위시리스트 추가
     *
     * @return
     */
    @PostMapping("/{placeId}")
    public Response<String> createWishlist(@PathVariable Long placeId, @RequestBody WishlistRequestDto wishlistRequestDto) {
        return Response.success(wishlistService.createWishlist(placeId, wishlistRequestDto));
    }

    /**
     * 위시리스트 삭제
     *
     * @return
     */
    @DeleteMapping("/{wishlistId}")
    public @ResponseBody
    Response<String> deleteWish(@PathVariable Long wishlistId) {
        return Response.success(wishlistService.deleteWishlist(wishlistId));
    }

    /**
     * 위시리스트 아이디로 리뷰 검색
     */
    @GetMapping("/{wishlist_id}")
    public Response<WishlistResponseDto> getWishlistByWishlist_id(@PathVariable Long wishlist_id) throws Exception {
        return Response.success(wishlistService.getWishlistBywishlist_id(wishlist_id));
    }

    /**
     * 회원으로 위시리스트 검색
     */
    @GetMapping("/client/{client_id}")
    public Response<List<WishlistResponseDto>> getWishlistClientId(@PathVariable Long client_id) {
        return Response.success(wishlistService.getWishlistByClientId(client_id));
    }

    /**
     * 자기 자신의 위시리스트 검색
     */
    @GetMapping
    public Response<List<WishlistResponseDto>> getMyWishlist() {
        return Response.success(wishlistService.getMyWishlist());
    }

    /**
     * 추천 시스템 기반 알고리즘 by Client_ID
     *
     * @return
     */
    @GetMapping("/run-python")
    public Response<JSONArray> runPythonScript() throws Exception {
        return Response.success(wishlistService.RecommentService());
    }
}