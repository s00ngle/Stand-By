package standby.standby.dto.profile.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.enums.ApplicantStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardCareerComponentDto {
    private Long boardId;
    private Long authorId;
    private Long applicantId;
    private ApplicantStatus applicantStatus;  // 지원 상태 (지원 완료, 불합격 등)
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;


    private Double score1;
    private Double score2;
    private Double score3;
    private Double score4;
    private Double score5;
    private String comment;
}

