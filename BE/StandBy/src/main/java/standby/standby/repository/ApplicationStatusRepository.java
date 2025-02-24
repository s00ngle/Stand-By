package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import org.springframework.data.jpa.repository.Query;
import standby.standby.entity.ApplicationStatus;
import standby.standby.enums.ApplicantStatus;
import standby.standby.entity.Board;
import standby.standby.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {

    /**
     * 특정 지원자가 지원한 모든 게시글 조회 (구인 중인 글에서 모ㄷ )
     */
    @Query("SELECT a FROM ApplicationStatus a JOIN FETCH a.board " +
            "WHERE a.applicant.userId = :applicantId AND a.board.status = true")
    List<ApplicationStatus> findByApplicantId(@Param("applicantId") Long applicantId);
    ApplicationStatus findByBoardAndApplicant(Board board, User applicant);
    List<ApplicationStatus> findByBoard(Board board);

    /**
     * 특정 지원자가 최종 합격한 게시글 조회
     */
    @Query("SELECT a FROM ApplicationStatus a JOIN FETCH a.board " +
            "WHERE a.applicant.userId = :applicantId " +
            "AND a.applicantStatus = :status " +
            "AND a.board.status = false")
    List<ApplicationStatus> findFinalAcceptedByApplicantId(@Param("applicantId") Long applicantId,
                                                           @Param("status") ApplicantStatus status);

    List<ApplicationStatus> findByBoardAndDeletedAtIsNull(Board board);

    // 지원자에게 가장 많이 지원한 카테고리
    @Query("SELECT c.category.categoryName FROM CategoryApplication c WHERE c.applicationStatus.applicant.userId = :userId GROUP BY c.category.categoryName ORDER BY COUNT(c.category.categoryName) DESC LIMIT 3")
    List<String> findTopCategoriesByApplicant(@Param("userId") Long userId);


    void deleteByBoardAndApplicant(Board board, User applicant);
}

