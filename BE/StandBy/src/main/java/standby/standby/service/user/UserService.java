package standby.standby.service.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import standby.standby.customException.AlreadyExistUser;
import standby.standby.customException.NotFoundKakaoAccessToken;
import standby.standby.customException.NotFoundKakaoUserInfo;
import standby.standby.customException.NotFoundUserId;
import standby.standby.entity.*;
import standby.standby.kakao.KakaoUtil;
import standby.standby.repository.BoardRepository;
import standby.standby.repository.PortfolioRepository;
import standby.standby.repository.ProfileImageRepository;
import standby.standby.repository.ProfileRepository;
import standby.standby.repository.user.UserRepository;
import standby.standby.service.BoardService;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final EntityManager em;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final KakaoUtil kakaoUtil;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final PortfolioRepository portfolioRepository;
    private final ProfileImageRepository profileImageRepository;

    // 회원가입 로직
    public User signUpForKakao(Map<String, Object> body) {

        Long kakaoId = ((Number) body.get("kakaoId")).longValue();
        String nickName = (String) body.get("nickname");
        String role = (String) body.get("role");

        // (만약에) 기존 유저가 존재하면 기존유저 반환
        // (무결성)
        User existUser = userRepository.findByKakaoId(kakaoId);

        if (existUser != null && existUser.getDeletedAt() == null) {
            throw new AlreadyExistUser("KakaoId already exist");
        }

        // 새로운 유저 만들기
        User newUser = new User();
        em.persist(newUser);
        newUser.setRole(role);
        newUser.setKakaoId(kakaoId);
        userRepository.save(newUser);

        // 새 프로필 만들어서 저장
        Profile newProfile = new Profile();
        em.persist(newProfile);
        newProfile.setNickname(nickName);
        newProfile.setUser(newUser);
        profileRepository.save(newProfile);

        ProfileImage defaultProfileImage = new ProfileImage();
        em.persist(defaultProfileImage);
        defaultProfileImage.setProfile(newProfile);
        defaultProfileImage.setImagePath("/images/default-avatar.png");
        defaultProfileImage.setOriName("default-avatar.png");
        defaultProfileImage.setSystemName("default-avatar.png"); 
        profileImageRepository.save(defaultProfileImage);

        Portfolio newPortfolio = new Portfolio();
        em.persist(newPortfolio);
        newPortfolio.setUser(newUser);
        portfolioRepository.save(newPortfolio);

        em.flush();
        em.clear();

        // 다시 해당 맴버 찾아서 profileImages까지 볼 수 있게 하고 반환
        User tmpUser = userRepository.findByKakaoId(kakaoId);
        if (tmpUser != null) {
            int imagesCount = tmpUser.getProfile().getProfileImages().size();
            System.out.println(tmpUser.getProfile().getNickname());
            return tmpUser;
        }
        throw new NotFoundUserId("KakaoId로 못찾음");
    }








    public Map<String, Object> signInForKakao(Map<String, String> body) {
        String code = body.get("code");
        // 카카오 액세스 토큰 요청
        String accessToken = kakaoUtil.getKakaoAccessToken(code);

        if (accessToken == null) {
            throw new NotFoundKakaoAccessToken("카카오 액세스 토큰을 가져올 수 없습니다.");
        }

        // 카카오 사용자 정보 요청
        Map<String, Object> userInfo = kakaoUtil.getKakaoUserInfo(accessToken);
        if (userInfo == null) {
            throw new NotFoundKakaoUserInfo("accessToken으로 UserInfo를 가져올 수 없습니다.");
        }

        Long kakaoId = (Long)userInfo.get("id");
        User user = userRepository.findByKakaoId(kakaoId);

        Map<String, Object> loginMap = new HashMap<>();

        if(user != null) {
            int imageCount = user.getProfile().getProfileImages().size();
        }
        loginMap.put("kakaoId", kakaoId);
        loginMap.put("user", user);
        return loginMap;
    }

    public int deleteUser(Long userId) {
        List<Board> boardList;
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundUserId("userId = " + userId + "로 user를 찾을 수 없습니다.");
        }

        boardList = user.get().getMyBoards();
        int boardListSize = boardList.size();
        for (Board board : boardList) {
            Long boardId = board.getBoardId();
            boardService.deleteBoard(boardId);
        }
        return userRepository.updateUserById(userId);
    }

    public Long getProfileIdByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저아이디로 유저를 찾을 수 없습니다"));
        return user.getProfile().getProfileId();
    }


}
