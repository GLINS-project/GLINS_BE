package GLINS_BE.GLINS.place.controller;

import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.dto.PlaceRequestDto;
import GLINS_BE.GLINS.place.service.PlaceService;
import GLINS_BE.GLINS.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/place")
public class PlaceController {

    private final PlaceService placeService;

    // 장소 크롤링 후 데이터 저장
    @PostMapping
    public Response<String> createPlace(@RequestBody PlaceRequestDto requestDto) {
        return Response.success(placeService.createPlace(requestDto));
    }

}
