package standby.standby.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import standby.standby.jwt.JWTUtil;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoUtil {
    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final WebClient.Builder webClientBuilder;
    private final JWTUtil jwtUtil;
    private final WebClient webClient;

    public String getKakaoAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String params = String.format(
                "grant_type=authorization_code&client_id=%s&redirect_uri=%s&code=%s",
                REST_API_KEY, REDIRECT_URI, code
        );

        return webClientBuilder.build()
                .post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(params)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .block(); // 동기 방식으로 처리
    }

    public Map<String, Object> getKakaoUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(); // 동기 방식으로 변환
    }
}
