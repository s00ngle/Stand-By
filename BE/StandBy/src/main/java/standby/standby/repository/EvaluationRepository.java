package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import standby.standby.dto.profile.component.EvaluationDetailDto;
import standby.standby.dto.profile.response.EvaluationResponseDto;
import standby.standby.entity.Board;
import standby.standby.entity.Evaluation;
import standby.standby.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT COALESCE(AVG(e.score1), 0.0), COALESCE(AVG(e.score2), 0.0), " +
            "COALESCE(AVG(e.score3), 0.0), COALESCE(AVG(e.score4), 0.0), COALESCE(AVG(e.score5), 0.0) " +
            "FROM Evaluation e " +
            "WHERE e.evaluatee.userId = :evaluateeId")
    Optional<List<Object[]>> getAverageScoresOnlyFromEvaluation(@Param("evaluateeId") Long evaluateeId);





    // 특정 지원자의 평균 평가 점수 조회
    @Query("SELECT COALESCE(AVG(e.score1), 0.0), COALESCE(AVG(e.score2), 0.0), " +
            "COALESCE(AVG(e.score3), 0.0), COALESCE(AVG(e.score4), 0.0), COALESCE(AVG(e.score5), 0.0) " +
            "FROM Evaluation e " +
            "JOIN ApplicationStatus a ON e.evaluatee.userId = a.applicant.userId " +
            "WHERE a.applicantStatus = '최종합격' AND a.applicant.userId = :evaluateeId")
    List<Double[]> getAverageScoresOfFinalAcceptedApplicants(@Param("evaluateeId") Long evaluateeId);

    // 평가받은 사용자(userId)에 대한 모든 평가 리스트 조회
    List<Evaluation> findByEvaluatee_UserId(Long userId);

    // 특정 지원자의 특정 게시물 평가 조회
    Evaluation findByBoard_BoardIdAndEvaluatee_UserId(Long boardId, Long evaluateeId);

    Evaluation findByEvaluatorAndEvaluateeAndBoard(User evaluator, User evaluatee, Board board);
}

