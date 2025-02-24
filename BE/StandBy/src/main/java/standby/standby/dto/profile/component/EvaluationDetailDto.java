package standby.standby.dto.profile.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDetailDto {
    private Long evaluatorId;
    private Long evaluateeId;
    private Long boardId;
    private String comment;
    private Double score1;
    private Double score2;
    private Double score3;
    private Double score4;
    private Double score5;
}