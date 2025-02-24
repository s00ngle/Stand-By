package standby.standby.dto.boardCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoDateDto {
    private Long applicantId;
    private Long boardId;
    private int applicantStatus;
    private String interviewMemo;
    private String updatedAt;

}
