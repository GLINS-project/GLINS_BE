package GLINS_BE.GLINS.place.domain;

import GLINS_BE.GLINS.client.domain.Role;
import lombok.*;

import javax.persistence.*;

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
    private Long likeNum;

    public void increaseLike() { this.likeNum++; }
}
