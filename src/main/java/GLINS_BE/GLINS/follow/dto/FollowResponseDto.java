package GLINS_BE.GLINS.follow.dto;

import GLINS_BE.GLINS.client.domain.Client;
import lombok.Builder;
import lombok.Data;

public class FollowResponseDto {

    @Data
    @Builder
    public static class Follow {
        private Long clientId;
        private String message;
    }

    @Data
    public static class Info {
        private Long clientId;
        private String nickname;
        private String imageUrl;

        public Info(Client client){
            this.clientId = client.getId();
            this.nickname = client.getNickname();
            this.imageUrl = client.getImageUrl();
        }
    }
}
