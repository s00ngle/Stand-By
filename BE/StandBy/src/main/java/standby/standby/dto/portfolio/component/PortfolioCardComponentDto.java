package standby.standby.dto.portfolio.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCardComponentDto {
    private Long portfolioCardId;
    private Long portfolioId;
    private Long typeCard;   // 포트폴리오 카드 유형(1: 유튜브, 2: 사진)
    private String title;    // 포트폴리오 카드 제목
    private String content;  // 포트폴리오 카드 내용
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 유튜브 정보 (typeCard = 1일 때만 사용)
    private PortfolioCardYoutubeInfo youtube;

    // 이미지 정보 (typeCard = 2일 때만 사용)
    private PortfolioCardImageInfo image;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortfolioCardYoutubeInfo {
        private Long portfolioCardYoutubeId;
        private String imbedUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortfolioCardImageInfo {
        private Long portfolioCardImageId;
        private String imagePath;
        private String oriName;
        private String systemName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}