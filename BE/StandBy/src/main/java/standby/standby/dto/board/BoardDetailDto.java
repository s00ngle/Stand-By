package standby.standby.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.dto.boardCommon.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {
//    private Long boardId;
//    private String nickname;
//    private String profileImgUrl;
//    private String title;
//    private String description;
//    private String genre;
//    private List<String> roles;
//    private List<LocationDto> location;
//    private DateDto date;
//    private int pay;
//    private String paymentType;
//    private int duration;
//    private int availablePositions;
//    private List<String> images;
//    private boolean status;
//    private boolean end;
//    private long authorId;
      private BoardResponseDto board;
      private List<ApplicationStatusDto> apllicantList;
}
