package GLINS_BE.GLINS.client.dto;

import lombok.Builder;
import lombok.Data;

public class ClientResponseDto {

    @Data
    @Builder
    public static class withdraw {
        private String message;
        private Long clientId;
    }

    @Data
    @Builder
    public static class logout {
        private String message;
    }
    @Data
    @Builder
    public static class updateNickname{
        private String nickname;
    }

    @Data
    @Builder
    public static class updateImage {
        private String imageUrl;
    }

    @Data
    @Builder
    public static class info{
        private Long id;
        private String nickname;
        private String imageUrl;
    }
}