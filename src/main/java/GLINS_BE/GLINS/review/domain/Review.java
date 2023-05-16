package GLINS_BE.GLINS.review.domain;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.place.domain.Place;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    // 여러 Review가 Client 한 명에 의해 작성될 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 여러 Review가 한 Place에 작성될 수 있음
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private String Content;
    private BigDecimal Rating;
}
