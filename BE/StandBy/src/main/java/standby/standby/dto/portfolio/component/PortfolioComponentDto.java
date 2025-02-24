package standby.standby.dto.portfolio.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioComponentDto {
    private Long portfolioId;
    private List<PortfolioCardComponentDto> portfolioCards;
}