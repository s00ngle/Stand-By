package standby.standby.dto.boardCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceDto {

    private String name;
    private String detail;
    private Double longitude;
    private Double latitude;

}
