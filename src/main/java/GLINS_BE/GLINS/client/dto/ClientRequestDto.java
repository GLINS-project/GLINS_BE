package GLINS_BE.GLINS.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRequestDto {
    private String nickname;
    private String imageUrl;
}