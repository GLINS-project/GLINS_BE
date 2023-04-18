package GLINS_BE.GLINS.client.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.dto.ClientRequestDto;
import GLINS_BE.GLINS.client.dto.ClientResponseDto;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 탈퇴
    public ClientResponseDto.withdraw withdraw(String email) {
        Client client = this.validateClient(email);
        clientRepository.deleteById(client.getId());
        return ClientResponseDto.withdraw.builder().clientId(client.getId()).message("회원 탈퇴 완료").build();
    }

    // 닉네임 변경
    public ClientResponseDto.updateNickname updateNickname(String email, ClientRequestDto requestDto) {
        Client client = this.validateClient(email);
        Client updatedClient = Client.builder()
                .id(client.getId())
                .email(client.getEmail())
                .password(client.getPassword())
                .nickname(requestDto.getNickname())
                .imageUrl(client.getImageUrl())
                .role(client.getRole())
                .socialType(client.getSocialType())
                .socialId(client.getSocialId())
                .refreshToken(client.getRefreshToken())
                .build();
        clientRepository.save(updatedClient);
        return ClientResponseDto.updateNickname.builder()
                .nickname(updatedClient.getNickname())
                .build();
    }

    // 이미지 변경
    public ClientResponseDto.updateImage updateImage(String email, ClientRequestDto requestDto) {
        Client client = this.validateClient(email);
        Client updatedClient = Client.builder()
                .id(client.getId())
                .email(client.getEmail())
                .password(client.getPassword())
                .nickname(client.getNickname())
                .imageUrl(requestDto.getImageUrl())
                .role(client.getRole())
                .socialType(client.getSocialType())
                .socialId(client.getSocialId())
                .refreshToken(client.getRefreshToken())
                .build();
        clientRepository.save(updatedClient);
        return ClientResponseDto.updateImage.builder()
                .imageUrl(updatedClient.getImageUrl())
                .build();
    }

    // 회원 검증 및 불러오기
    private Client validateClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
    }

}
