package standby.standby.dto.boardCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusDto {
    private Long applicantId;
    private Long employerId;
    private Long boardId;
    private String applicantStatus;  // 0: 지원 완료, 1: 서류 합격, 2: 면접 합격, 3: 최종 합격 등
    private String Memo;  // 면접자 메모
    private String updatedAt;
    private String role;
    private String profileImage;
    private String nickname;

}
