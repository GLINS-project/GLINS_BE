package GLINS_BE.GLINS.wishlist.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.config.SecurityUtil;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import GLINS_BE.GLINS.review.domain.Review;
import GLINS_BE.GLINS.review.dto.ReviewResponseDto;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import GLINS_BE.GLINS.wishlist.dto.WishlistRequestDto;
import GLINS_BE.GLINS.wishlist.dto.WishlistResponseDto;
import GLINS_BE.GLINS.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final PlaceRepository placeRepository;
    private final ClientRepository clientRepository;

    /**
     * 위시리스트 추가
     */
    public String createWishlist(Long placeId, WishlistRequestDto wishlistRequestDto) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.PLACE_NOT_FOUND, ErrorCode.PLACE_NOT_FOUND.getMessage()));
        Wishlist createWishlist = Wishlist.builder().client(client).place(place).wishlist_kind(wishlistRequestDto.getWishlist_kind()).build();
        wishlistRepository.save(createWishlist);
        return "리뷰 등록 완료";
    }


    /**
     * 위시리스트 삭제
     * @param wishlist_id
     */
    public String deleteWishlist(Long wishlist_id) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Wishlist wishlist = validateWishlist(wishlist_id);

        // 현재 사용자가 작성한 리뷰인지 확인하는 로직
        if(client == wishlist.getClient()){
            wishlistRepository.delete(wishlist);
        }else {
            throw new AllGlinsException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        return "위시리스트 삭제 완료";
    }

    /**
     * 위시리스트 ID로 조회
     */
    public WishlistResponseDto getWishlistBywishlist_id(Long wishlist_id) {
        return new WishlistResponseDto(validateWishlist(wishlist_id));
    }

    /**
     * ClientId로 위시리스트 조회
     */
    public List<WishlistResponseDto> getWishlistByClientId(Long clientId) {
        return wishlistRepository.findByClient_Id(clientId).stream()
                .map(WishlistResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 자신의 위시리스트 모두 조회
     */
    public List<WishlistResponseDto> getMyWishlist() {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        return wishlistRepository.findByClient_Id(client.getId()).stream()
                .map(WishlistResponseDto::new)
                .collect(Collectors.toList());
    }

    private Client validateClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
    }

    private Wishlist validateWishlist(Long wishlist_id) {
        return wishlistRepository.findById(wishlist_id).orElseThrow(() ->
                new AllGlinsException(ErrorCode.WISHLIST_NOT_FOUND, ErrorCode.WISHLIST_NOT_FOUND.getMessage()));
    }
}
