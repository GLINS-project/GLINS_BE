package GLINS_BE.GLINS.follow.repository;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowingId(Client client);
    List<Follow> findByFollowerId(Client client);

    Optional<Follow> findByFollowerIdAndFollowingId(Client follower, Client following);
}
