package standby.standby.controller.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import standby.standby.customException.AlreadyExistUser;
import standby.standby.customException.NotFoundKakaoAccessToken;
import standby.standby.customException.NotFoundKakaoUserInfo;
import standby.standby.customException.NotFoundUserId;
import standby.standby.dto.login.LoginResponseDto;
import standby.standby.entity.User;
import standby.standby.jwt.JWTUtil;
import standby.standby.kakao.KakaoUtil;
import standby.standby.repository.ProfileRepository;
import standby.standby.repository.user.UserRepository;
import standby.standby.service.BoardService;
import standby.standby.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final BoardService boardService;
    private final JWTUtil jwtUtil;

    // 회원가입 로직
    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> kakaoSignUp(@RequestBody Map<String, Object> body) {
        User newUser;
        User user;
        try {
            // 해당 유저는 프로필 이미지까지 접근가능
            newUser = userService.signUpForKakao(body);

            String jwtToken = jwtUtil.createJwt(newUser.getKakaoId(), 1000 * 60 * 60 * 10L);
            Map<String, Object> map = new HashMap<>();
            map.put("token", jwtToken);
            map.put("user", new LoginResponseDto(newUser));
            return ResponseEntity.ok(map);
        } catch (NotFoundKakaoAccessToken | NotFoundKakaoUserInfo | AlreadyExistUser e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());;
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        User user;
        Long kakaoId;
        try {
            // 로그인 시도해보고 없으면 null 반환
            Map<String, Object> loginMap = userService.signInForKakao(body);
            user = (User) loginMap.get("user");
            kakaoId = (Long) loginMap.get("kakaoId");

            Map<String, Object> map = new HashMap<>();
            if (user == null) {
                map.put("kakaoId", kakaoId);
                map.put("isAuthenticated", false);
                map.put("user", null);
            } else {
                String jwtToken = jwtUtil.createJwt(user.getKakaoId(), 1000 * 60 * 60 * 10L);
                map.put("isAuthenticated", true);
                map.put("token", jwtToken);
                map.put("user", new LoginResponseDto(user));
            }

            return ResponseEntity.ok(map);

        } catch (NotFoundKakaoAccessToken | NotFoundKakaoUserInfo e) {

            Map<String, Object> errorResponse = new HashMap<>();

            errorResponse.put("message", e.getMessage());;

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // 지워진 사람 또 지워도 200 성공 메시지 가면서 update문 날라감.
    // 나중에 수정 필요함.
    @CrossOrigin
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> login(
            @PathVariable("userId") Long userId) {
//        @RequestHeader("Authorization") String token
        int deleteCount;
        try {
             deleteCount = userService.deleteUser(userId);
        } catch (NotFoundUserId e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Map<String, Object> map = new HashMap<>();
        if (deleteCount == 1) {
            map.put("deleteCount", deleteCount);
            return ResponseEntity.ok(map);
        } else {
            map.put("deleteCount", deleteCount);
            return ResponseEntity.badRequest().body(map);
        }
    }
}