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
    }
}
