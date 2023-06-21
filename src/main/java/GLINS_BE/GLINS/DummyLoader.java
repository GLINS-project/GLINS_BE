package GLINS_BE.GLINS;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.domain.Role;
import GLINS_BE.GLINS.client.domain.SocialType;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.place.domain.Place;
import GLINS_BE.GLINS.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DummyLoader {

    private final ClientRepository clientRepository;
    private final PlaceRepository placeRepository;

    @PostConstruct
    public void loadData(){
        Client client1 = new Client(1L, new ArrayList<>(), "test@test.com", "", "조용헌",
                "", Role.USER, SocialType.KAKAO, "", "");
        clientRepository.save(client1);
        Client client2 = new Client(2L, new ArrayList<>(), "1234@1234.com", "", "여인국",
                "", Role.USER, SocialType.GOOGLE, "", "");
        clientRepository.save(client2);
        Client client3 = new Client(3L, new ArrayList<>(), "abcd@abcd.com", "", "박정준",
                "", Role.USER, SocialType.KAKAO, "", "");
        clientRepository.save(client3);

        Place place1 = new Place(1L, "홍익대학교", 32.32423, 36.23412);
        placeRepository.save(place1);
        Place place2 = new Place(2L, "호계초등학교", 33.32423, 35.23412);
        placeRepository.save(place2);
    }
}
