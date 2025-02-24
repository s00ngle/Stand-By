package standby.standby.dto.portfolio.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCardRequestDto {
    private String title;
    private String content;
    private String youtubeUrl;
    private Long imageId;
}
