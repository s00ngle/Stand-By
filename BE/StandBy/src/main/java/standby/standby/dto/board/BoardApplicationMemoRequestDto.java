package standby.standby.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.boardCommon.ApplicationStatusDto;
import standby.standby.enums.ApplicantStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardApplicationMemoRequestDto {
    private String memos;
}
