package GLINS_BE.GLINS.place.dto;

import lombok.Data;

@Data
public class PlaceRequestDto {
    private String placeName;
    private double latitude;
    private double longitude;
}
