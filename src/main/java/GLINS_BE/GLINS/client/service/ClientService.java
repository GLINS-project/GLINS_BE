package GLINS_BE.GLINS.client.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.dto.ClientRequestDto;
import GLINS_BE.GLINS.client.dto.ClientResponseDto;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.config.SecurityUtil;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final S3Util s3Util;

    // 내 정보 얻어오기(프로필 화면을 위함)
    public ClientResponseDto.info getInfo(){
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        return ClientResponseDto.info.builder().nickname(client.getNickname()).imageUrl(client.getImageUrl()).build();
    }

    // 다른 사용자의 정보 얻어오기
    public ClientResponseDto.info getOtherInfo(Long clientId){
        Client other = clientRepository.findById(clientId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
        return ClientResponseDto.info.builder().nickname(other.getNickname()).imageUrl(other.getImageUrl()).build();
    }

    // 닉네임 변경
    public ClientResponseDto.updateNickname updateNickname(ClientRequestDto requestDto) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        client.updateNickname(requestDto.getNickname());
        return ClientResponseDto.updateNickname.builder().nickname(requestDto.getNickname()).build();
    }

    // 이미지 변경
    public ClientResponseDto.updateImage updateImage(ClientRequestDto requestDto) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);

        if (requestDto.getProfileImage().isEmpty()) throw new AllGlinsException(ErrorCode.INVALID_REQUEST, "Profile image is empty");
        String imageUrl = s3Util.uploadFiles(requestDto.getProfileImage(), "profile");

        client.updateImage(imageUrl);
        return ClientResponseDto.updateImage.builder().imageUrl(imageUrl).build();
    }

    // 회원 탈퇴
    public ClientResponseDto.withdraw withdraw() {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        clientRepository.deleteById(client.getId());
        return ClientResponseDto.withdraw.builder().clientId(client.getId()).message("회원 탈퇴 완료").build();
    }

    // 회원 검증 및 불러오기
    private Client validateClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
    }
}
