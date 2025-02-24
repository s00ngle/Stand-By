package standby.standby.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import standby.standby.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoginResponseDto {

    private Long userId;
    private String nickName;
    private String role;
    private List<String> profileImageUrl = new ArrayList<>();


    public LoginResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickName = user.getProfile().getNickname();
        this.role = user.getRole();
        int size = user.getProfile().getProfileImages().size();
        if (size == 0) {
            this.profileImageUrl = null;
        } else {
            for (int i = 0; i < size; i++) {
                this.profileImageUrl.add("https://i12b211.p.ssafy.io/api" + user.getProfile().getProfileImages().get(i).getImagePath());
            }
        }
    }

}
