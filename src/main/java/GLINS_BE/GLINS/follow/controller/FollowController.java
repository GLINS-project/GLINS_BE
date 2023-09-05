package GLINS_BE.GLINS.follow.controller;

import GLINS_BE.GLINS.follow.dto.FollowResponseDto;
import GLINS_BE.GLINS.follow.service.FollowService;
import GLINS_BE.GLINS.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{clientId}")
    public Response<FollowResponseDto.Follow> follow(@PathVariable Long clientId){
        return Response.success(followService.follow(clientId));
    }

    @GetMapping
    public Response<List<FollowResponseDto.Info>> getMyFollower(){
        return Response.success(followService.getMyFollower());
    }

    @GetMapping("/following")
    public Response<List<FollowResponseDto.Info>> getMyFollowing(){
        return Response.success(followService.getMyFollowing());
    }

    @DeleteMapping("/{clientId}")
    public Response<FollowResponseDto.Follow> unfollow(@PathVariable Long clientId){
        return Response.success(followService.unfollow(clientId));
    }
}
