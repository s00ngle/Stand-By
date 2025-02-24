package standby.standby.dto.boardCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 게시판 지원자
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardApplicantDto {
    private  long applicantId;
    private  long employeeId;
    private  long boardId;
    private boolean applicantStatus;
    private String Interview_memo;
    private String createdAt;
    private String updatedAt;
    private String deleteAt;


}
