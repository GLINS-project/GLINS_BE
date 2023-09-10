package GLINS_BE.GLINS.place.domain;

import GLINS_BE.GLINS.client.domain.Role;
import GLINS_BE.GLINS.review.domain.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private String placeName;
    private double latitude;
    private double longitude;
    private String category;
    private String hashtag;
}
