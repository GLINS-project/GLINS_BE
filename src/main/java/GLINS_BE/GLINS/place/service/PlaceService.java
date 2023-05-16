package GLINS_BE.GLINS.place.service;

import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.dto.PlaceRequestDto;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

// 장소가 이미 존재하는지 확인하기
//    private Place validatePlace(PlaceRequestDto requestDto) {
//        Place place = placeRepository.findByPlaceNameAndLatitudeAndLongitude(requestDto.getPlaceName(),
//                requestDto.getLatitude(), requestDto.getLongitude()).orElse(createPlace(requestDto));
//        return place;
//    }

    public Place createPlace(PlaceRequestDto requestDto) {
        Place place = Place.builder().placeName(requestDto.getPlaceName()).latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude()).build();
        return placeRepository.save(place);
    }
}
