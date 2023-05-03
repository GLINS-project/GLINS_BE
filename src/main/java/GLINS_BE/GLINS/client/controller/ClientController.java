package GLINS_BE.GLINS.client.controller;

import GLINS_BE.GLINS.client.dto.ClientRequestDto;
import GLINS_BE.GLINS.client.dto.ClientResponseDto;
import GLINS_BE.GLINS.client.service.ClientService;
import GLINS_BE.GLINS.auth.jwt.service.JwtService;
import GLINS_BE.GLINS.exception.AllGlinsException;
import GLINS_BE.GLINS.exception.ErrorCode;
import GLINS_BE.GLINS.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client")
public class ClientController {

    private final JwtService jwtService;
    private final ClientService clientService;

    @GetMapping("/logout")
    public Response<ClientResponseDto.logout> logout() {
        ClientResponseDto.logout logoutResponse = jwtService.logout();
        return Response.success(logoutResponse);
    }

    @GetMapping
    public Response<ClientResponseDto.info> getInfo() {
        ClientResponseDto.info infoResponse = clientService.getInfo();
        return Response.success(infoResponse);
    }

    @DeleteMapping
    public Response<ClientResponseDto.withdraw> withdraw(){
        ClientResponseDto.withdraw withdrawResponse = clientService.withdraw();
        return Response.success(withdrawResponse);
    }

    @PatchMapping("/nickname")
    public Response<ClientResponseDto.updateNickname> updateNickname(@RequestBody ClientRequestDto requestDto){
        ClientResponseDto.updateNickname updateNicknameResponse =
                clientService.updateNickname(requestDto);
        return Response.success(updateNicknameResponse);
    }

    @PatchMapping("/image")
    public Response<ClientResponseDto.updateImage> updateImage(@RequestBody ClientRequestDto requestDto){
        ClientResponseDto.updateImage updateImageResponse =
                clientService.updateImage(requestDto);
        return Response.success(updateImageResponse);
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/test")
    public Response<?> test() {
        throw new AllGlinsException(ErrorCode.CLIENT_NOT_FOUND, ErrorCode.CLIENT_NOT_FOUND.getMessage());
    }
}