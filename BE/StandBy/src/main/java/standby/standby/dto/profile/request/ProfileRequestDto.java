package standby.standby.dto.profile.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileRequestDto {
    private Long userId;
    private String nickname;
    private String introduction;
    private String role;

    // employee (구직자)일 때만 사용
    private String gender;
    private Integer height;
    private Integer weight;
    private String workCounts;
    private String workYears;
    private LocalDate birthDate;

    // EMPLOYER (고용주)일 때만 사용
    private Integer recruitingCount;
    private String mostGenre;
    private List<String> mostRoleList;
}

