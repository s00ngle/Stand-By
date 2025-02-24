package standby.standby.dto.portfolio.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import standby.standby.entity.Portfolio;
import standby.standby.entity.PortfolioCard;
import standby.standby.entity.PortfolioCardImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PortfolioCardResponseDto {
    private Long cardId;
    private String title;
    private String youtubeUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imageUrl;

    public PortfolioCardResponseDto(PortfolioCard portfolioCards) {
        this.cardId = portfolioCards.getPortfolioCardId();
        this.title = portfolioCards.getTitle();
        this.youtubeUrl = portfolioCards.getYoutubeUrl();
        this.content = portfolioCards.getContent();
        this.createdAt = portfolioCards.getCreatedAt();
        this.updatedAt = portfolioCards.getUpdatedAt();
        this.imageUrl = new ArrayList<>();

        for (PortfolioCardImage images:portfolioCards.getPortfolioCardImage()) {
            this.imageUrl.add("https://i12b211.p.ssafy.io/api" + images.getImagePath());
        }
    }
}
