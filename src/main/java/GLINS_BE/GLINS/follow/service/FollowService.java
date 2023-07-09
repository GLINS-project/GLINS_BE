package GLINS_BE.GLINS.follow.service;

import GLINS_BE.GLINS.client.domain.Client;
import GLINS_BE.GLINS.client.repository.ClientRepository;
import GLINS_BE.GLINS.config.SecurityUtil;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.follow.domain.Follow;
import GLINS_BE.GLINS.follow.dto.FollowResponseDto;
import GLINS_BE.GLINS.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final ClientRepository clientRepository;

    // 팔로우
    @Transactional
    public FollowResponseDto.Follow follow(Long clientId) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Client other = clientRepository.findById(clientId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
        Follow follow = Follow.builder().followerId(client).followingId(other).build();
        followRepository.save(follow);
        return FollowResponseDto.Follow.builder().clientId(clientId).message("팔로우 완료").build();
    }

    // 나를 팔로우하는 사람 불러오기
    public List<FollowResponseDto.Info> getMyFollower() {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        List<Follow> followers = followRepository.findByFollowingId(client);
        return followers.stream().map(follow -> new FollowResponseDto.Info(follow.getFollowerId()))
                .collect(Collectors.toList());
    }

    // 내가 팔로우한 사람 불러오기
    public List<FollowResponseDto.Info> getMyFollowing() {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        List<Follow> followers = followRepository.findByFollowerId(client);
        return followers.stream().map(follow -> new FollowResponseDto.Info(follow.getFollowingId()))
                .collect(Collectors.toList());
    }

    // 언팔로우
    @Transactional
    public FollowResponseDto.Follow unfollow(Long clientId) {
        String email = SecurityUtil.getEmail();
        Client client = validateClient(email);
        Client other = clientRepository.findById(clientId).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
        Follow follow = followRepository.findByFollowerIdAndFollowingId(client, other).orElseThrow(() ->
                new AllGlinsException(ErrorCode.FOLLOW_NOT_FOUND, ErrorCode.FOLLOW_NOT_FOUND.getMessage()));
        followRepository.delete(follow);
        return FollowResponseDto.Follow.builder().clientId(clientId).message("언팔로우 완료").build();
    }

    private Client validateClient(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() ->
                new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage()));
    }
}
