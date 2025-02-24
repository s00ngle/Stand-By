package standby.standby.dto.portfolio.response;

import lombok.Getter;
import lombok.Setter;
import standby.standby.entity.PortfolioCard;
import standby.standby.entity.PortfolioCardImage;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardImageResponseDto {
    private Long cardId;
    private List<Long> imageIds = new ArrayList<>();
    public CardImageResponseDto(PortfolioCard card) {
        this.cardId = card.getPortfolioCardId();
        List<PortfolioCardImage> images = card.getPortfolioCardImage();
        if (images != null) {
            for (PortfolioCardImage image : images) {
                imageIds.add(image.getPortforlioCardImageId());
            }
        }
    }
}
