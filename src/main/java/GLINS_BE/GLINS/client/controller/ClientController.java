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
        return Response.success(jwtService.logout());
    }

    @GetMapping
    public Response<ClientResponseDto.info> getInfo() {
        return Response.success(clientService.getInfo());
    }

    @GetMapping("/{clientId}")
    public Response<ClientResponseDto.info> getOtherInfo(@PathVariable Long clientId){
        return Response.success(clientService.getOtherInfo(clientId));
    }

    @DeleteMapping
    public Response<ClientResponseDto.withdraw> withdraw(){
        return Response.success(clientService.withdraw());
    }

    @PatchMapping("/nickname")
    public Response<ClientResponseDto.updateNickname> updateNickname(@RequestBody ClientRequestDto requestDto){
        return Response.success(clientService.updateNickname(requestDto));
    }

    @PatchMapping("/image")
    public Response<ClientResponseDto.updateImage> updateImage(@RequestBody ClientRequestDto requestDto){
        return Response.success(clientService.updateImage(requestDto));
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