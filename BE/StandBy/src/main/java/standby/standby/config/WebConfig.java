package standby.standby.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 이미지 파일 서빙 설정
        String uploadPath = uploadDir.endsWith("/") ? uploadDir : uploadDir + "/";
        registry.addResourceHandler("/api/images/**")
                .addResourceLocations("file:" + uploadPath);
//                .addResourceLocations("file:///C:/images/");
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 API에 CORS 적용
                        .allowedOrigins("https://i12b211.p.ssafy.io", "http://localhost:3000")  // 허용할 도메인
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  // 허용할 HTTP 메서드
                        .allowedHeaders("*")  // 모든 헤더 허용
                        .allowCredentials(true)  // 쿠키 포함 허용
                        .maxAge(3600);  // 1시간 동안 pre-flight 캐싱
            }
        };
    }

}