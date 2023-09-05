package GLINS_BE.GLINS.wishlist.controller;

import GLINS_BE.GLINS.response.Response;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import GLINS_BE.GLINS.wishlist.dto.WishlistRequestDto;
import GLINS_BE.GLINS.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    /**
     기본 페이지 설정
     */
    @GetMapping("/v1/wishlist")
    public String Wishlist() {
        return "wishlist";
    }

    /**
    위시리스트 추가
     * @return
     */
    @PostMapping("/v1/wishlist")
    public @ResponseBody Response<Wishlist> create(@RequestParam(value = "user_id") Long user_id, @RequestParam(value = "place_id")
            Long place_id, @RequestBody WishlistRequestDto requestDto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser_id(user_id);
        wishlist.setPlace_id(place_id);
        wishlist.setWishlist_kind(requestDto.getWishlist_kind());

        Wishlist creatWishlist = wishlistService.join(wishlist);

        return Response.success(creatWishlist);
    }
    /**
     위시리스트 삭제
     * @return
     */
    @DeleteMapping("/v1/wishlist/:wishlistId")
    public @ResponseBody
    Response<String> deleteWish(@RequestParam Long wishlist_id){
        Wishlist find_wishlist = wishlistService.findOne(wishlist_id);
        wishlistService.getout(find_wishlist);
        return Response.success("DeleteComplete");
    }
    /**
     사용자로 위시리스트 검색
     */
    @GetMapping("/v1/wishlist/:userId")
    public @ResponseBody List<Wishlist> findbywishlistId(@RequestParam Long user_Id){
        List<Wishlist> find_Wishlist_by_userId = wishlistService.findbyuser(user_Id);
        for(int i=0;i<find_Wishlist_by_userId.size();i++){
            System.out.println("Reviews = " + find_Wishlist_by_userId.get(i).getWishlist_kind());
        }
        return find_Wishlist_by_userId;
    }

}
