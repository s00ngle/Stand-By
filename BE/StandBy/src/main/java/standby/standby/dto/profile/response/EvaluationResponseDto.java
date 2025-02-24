package standby.standby.dto.profile.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.profile.component.EvaluationDetailDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluationResponseDto {
    private Long userId;
    private String nickname;
    private String introduction;
    private String profileImage;
    private List<Double> scores;
    private String mostGenre;
    private List<String> mostRoleList;
    private List<EvaluationDetailDto> evaluationList;

}