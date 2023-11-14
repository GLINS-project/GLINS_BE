package GLINS_BE.GLINS.wishlist.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.config.SecurityUtil;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import GLINS_BE.GLINS.wishlist.dto.WishlistRequestDto;
import GLINS_BE.GLINS.wishlist.dto.WishlistResponseDto;
import GLINS_BE.GLINS.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        return "위시리스트 등록 완료";
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

    /**
     추천 시스템 기반 알고리즘
     * @return
     */
    public List<WishlistResponseDto> RecommentService() throws Exception {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        String client_id_for_Reco_Str = wishlistRepository.findByClient_Id(client.getId()).stream().map(WishlistResponseDto::new)
                .map(WishlistResponseDto::getClient_id)
                .collect(Collectors.toList())
                .toString()
                .trim();

        client_id_for_Reco_Str = client_id_for_Reco_Str.replace("[","").replace("]","").substring(0,1);

        String pythonExePath = "python3"; // 혹은 "python3", 시스템에 따라 다를 수 있습니다.
        String scriptPath = "../connect_py_db_v2.py";

        ProcessBuilder processBuilder = new ProcessBuilder(pythonExePath,scriptPath,client_id_for_Reco_Str);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n"); // 파이썬 스크립트의 출력을 output StringBuilder에 추가
        }

        // 정규표현식으로 place_id 추출
        String regex = "\"place_id\":\"(\\d+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(output);

        List<Integer> place_ids = new ArrayList<>();

        // 매칭된 모든 place_id 출력
        while (matcher.find()) {
            String placeId = matcher.group(1);
            place_ids.add(Integer.valueOf(placeId));
            System.out.println("Found place_id: " + placeId);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
        }

        return place_ids.stream()
                .flatMap(placeId -> placeRepository.findById(Long.valueOf(placeId)).stream())
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
