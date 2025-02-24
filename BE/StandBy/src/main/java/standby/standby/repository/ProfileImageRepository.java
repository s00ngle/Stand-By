package standby.standby.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import standby.standby.entity.ProfileImage;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    // 특정 프로필 ID에 해당하는 모든 이미지 조화
    List<ProfileImage> findByProfile_ProfileId(Long profileId);
}
