package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> { // ✅ profileId는 Long 타입으로 수정

    // profileId로 프로필 조회
    Optional<Profile> findById(Long profileId);

    // userId를 기준으로 프로필 찾기 (기존 코드 유지)
    Optional<Profile> findByUser_UserId(Long userId);

}