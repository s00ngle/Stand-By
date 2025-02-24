package standby.standby.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import standby.standby.entity.Board;
import standby.standby.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 카카오 아이디로 사용자 찾기
    User findByKakaoId(Long kakaoId);

    @Modifying
    @Query( "UPDATE User u " +
            "SET u.deletedAt = CURRENT_TIMESTAMP, u.kakaoId = null " +
            "WHERE u.userId = :userId AND u.deletedAt IS NULL")
    int updateUserById(@Param("userId") Long userId);
}
