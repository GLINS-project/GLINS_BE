package GLINS_BE.GLINS.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClientRequestDto {
    private String nickname;
    private MultipartFile profileImage;
}