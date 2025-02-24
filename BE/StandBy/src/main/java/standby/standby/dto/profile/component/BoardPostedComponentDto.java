package standby.standby.dto.profile.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardPostedComponentDto {
    private Long boardId;
    private Long authorId;
    private String authorName;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;


}

