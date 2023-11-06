package GLINS_BE.GLINS.place.dto;

import GLINS_BE.GLINS.place.domain.Place;
import lombok.Data;

@Data
public class PlaceRequestDto {
    private String placeName;
    private String address;
    private double latitude;
    private double longitude;

    public Place toEntity(String category, String hashtag, String imgUrl) {
        return Place.builder()
                .placeName(placeName)
                .latitude(latitude)
                .longitude(longitude)
                .category(category)
                .hashtag(hashtag)
                .imgUrl(imgUrl)
                .build();
    }
}
