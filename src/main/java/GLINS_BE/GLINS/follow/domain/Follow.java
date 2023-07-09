package GLINS_BE.GLINS.follow.domain;

import GLINS_BE.GLINS.client.domain.Client;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른 패키지에 소속된 클래스에서 접근 불가 (상속 제외)
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    private Client followerId;

    @ManyToOne
    private Client followingId;
}
