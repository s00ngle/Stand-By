package standby.standby.dto.profile.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileComponentDto {
    private String role;
    private Long userId;
    private String nickname;
    private String introduction;
    private LocalDate birthDate;
    private String gender;

    ////// 구직자
    private Integer height;
    private Integer weight;
    private List<Double> scores;
    ////////////////////////////


    private String workCounts;
    private String workYears;
    private String profileImage;

    /////// 구인자
    private Integer recruitingCount;
    private String mostGenre;
    private List<String> mostRoleList;
    ///////////////////////////////


}
