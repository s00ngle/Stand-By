package standby.standby.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.boardCommon.ApplicationStatusDto;

// 거절, 승낙, 최종승낙등 모든상태 표시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardApplicationStatusResponseDto {
    private String message;
    private ApplicationStatusDto applicantStatus;


}
