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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardAppliedComponentDto {
    private Long boardId;
    private Long authorId;
    private Long applicantId;
    private ApplicantStatus applicantStatus;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;



}
