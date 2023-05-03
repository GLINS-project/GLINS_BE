package GLINS_BE.GLINS.client.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른 패키지에 소속된 클래스에서 접근 불가 (상속 제외)
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 기본키 생성을 위임
    @Column(name = "client_id")
    private Long id;

    private String email; // 이메일
    private String password; // 비밀번호
    private String nickname; // 닉네임
    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값

    private String refreshToken; // 리프레시 토큰

    // 유저 권한 설정 메소드
    public void authorizeClient() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void updateImage(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
