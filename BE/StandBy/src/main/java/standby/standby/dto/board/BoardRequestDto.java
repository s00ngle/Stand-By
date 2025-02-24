package standby.standby.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.boardCommon.AttachmentDto;
import standby.standby.dto.boardCommon.CategoryDto;
import standby.standby.dto.boardCommon.WorkplaceDto;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private String payType;
    private int pay;
    private int duration;
    private boolean status;  // 구인중: true, 구인완료: false
    private Long authorId;
    private String genre;
    private List<String> roles;
    private List<WorkplaceDto> location;
    private int availablePositions;

}
