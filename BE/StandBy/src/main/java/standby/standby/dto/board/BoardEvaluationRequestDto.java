package standby.standby.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEvaluationRequestDto {
    private Long evaluatorId;
    private Long boardId;
    private Long evaluateeId;
    private String comment;
    private double score1;
    private double score2;
    private double score3;
    private double score4;
    private double score5;
}
