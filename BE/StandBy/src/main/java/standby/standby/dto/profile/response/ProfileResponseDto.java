package standby.standby.dto.profile.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.profile.component.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDto {

    private ProfileComponentDto profile; // 공통 프로필 정보

    // 구직자 게시물
    private List<BoardAppliedComponentDto> appliedBoards; // 지원한 게시물
    private List<BoardCareerComponentDto> careerBoards; // 경력 게시물 (최종합격 + 평가완료 + 구인마감)

    // 구인자 게시물
    private List<BoardPostedComponentDto> activeBoards; // 현재 구인 중인 게시물
    private List<BoardPostedComponentDto> closedBoards; // 구인 마감된 게시물
}
