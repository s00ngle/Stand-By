package standby.standby.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import standby.standby.dto.profile.component.EvaluationDetailDto;
import standby.standby.dto.profile.response.EvaluationResponseDto;
import standby.standby.entity.ApplicationStatus;
import standby.standby.entity.Evaluation;
import standby.standby.entity.Profile;
import standby.standby.entity.ProfileImage;
import standby.standby.enums.ApplicantStatus;
import standby.standby.repository.ApplicationStatusRepository;
import standby.standby.repository.CategoryBoardRepository;
import standby.standby.repository.EvaluationRepository;
import standby.standby.repository.ProfileRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final ProfileRepository profileRepository;
    private final EvaluationRepository evaluationRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final CategoryBoardRepository categoryBoardRepository;

    public EvaluationResponseDto getEvaluation(Long userId) {
        // 1. 프로필 정보 조회
        Profile profile = profileRepository.findById(userId).orElse(null);
        if (profile == null) {
            return null;
        }

        // 2. 최종 합격한 지원 내역 조회
        List<ApplicationStatus> finalAcceptedApplications =
                applicationStatusRepository.findFinalAcceptedByApplicantId(userId, ApplicantStatus.최종합격);

        // 3. 평가 정보 조회 (Evaluation 엔티티 리스트 반환)
        List<Evaluation> evaluations = evaluationRepository.findByEvaluatee_UserId(userId);

        // 4. DTO 변환
        List<EvaluationDetailDto> evaluationDetails = evaluations.stream()
                .map(this::convertToEvaluationDetailDto)
                .collect(Collectors.toList());

        if (evaluationDetails.isEmpty()) {
            evaluationDetails.add(new EvaluationDetailDto(null, null, null, "평가 없음", null, null, null, null, null));
        }

        // 5. 가장 많이 받은 장르 1개 및 역할 최대 3개 조회
        List<String> topCategories = categoryBoardRepository.findTopCategoriesByApplicant(userId);
        String mostGenre = !topCategories.isEmpty() ? topCategories.get(0) : null;
        List<String> mostRoleList = topCategories.size() > 1
                ? topCategories.subList(1, Math.min(4, topCategories.size()))
                : Collections.emptyList();

        // 6. 프로필 이미지 가져오기
        String profileImagePath = null;
        if (profile.getProfileImages() != null && !profile.getProfileImages().isEmpty()) {
            ProfileImage latestImage = profile.getProfileImages().stream()
                    .max(Comparator.comparing(ProfileImage::getCreatedAt))
                    .orElse(null);
            if (latestImage != null) {
                profileImagePath = latestImage.getImagePath();
            }
        }
        if (profileImagePath != null && !profileImagePath.startsWith("http")) {
            profileImagePath = "https://i12b211.p.ssafy.io/api" + profileImagePath;
        }

        // 1. 모든 지원 내역 조회
        List<ApplicationStatus> allApplications = applicationStatusRepository.findByApplicantId(profile.getUser().getUserId());

        // 2. 평가 점수 가져오기 (평균이 없으면 0.0으로 초기화)
        List<Object[]> avgScoresList = evaluationRepository.getAverageScoresOnlyFromEvaluation(profile.getUser().getUserId()).orElse(Collections.emptyList());

        // 3. 기본값 설정
        Double[] scores = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};

        // 4. 리스트가 비어있지 않다면 첫 번째 요소 사용
        if (!avgScoresList.isEmpty() && avgScoresList.get(0) != null) {
            Object[] result = avgScoresList.get(0);
            System.out.println("평균 점수 원본 데이터: " + Arrays.toString(result)); // 디버깅 로그

            for (int i = 0; i < scores.length; i++) {
                if (result[i] instanceof Number) {
                    scores[i] = ((Number) result[i]).doubleValue();
                } else {
                    System.out.println("잘못된 데이터 감지: " + result[i]); // 디버깅 로그 추가
                }
            }
        }

        // 5. 평균 점수 소수점 한 자리 반올림
        List<Double> roundedScores = Arrays.asList(
                Math.round(scores[0] * 10.0) / 10.0,
                Math.round(scores[1] * 10.0) / 10.0,
                Math.round(scores[2] * 10.0) / 10.0,
                Math.round(scores[3] * 10.0) / 10.0,
                Math.round(scores[4] * 10.0) / 10.0
        );

        System.out.println("평균 점수: " + roundedScores); // 디버깅 로그 추가

        // 8. 최종 EvaluationResponseDto 생성 및 반환
        return new EvaluationResponseDto(
                userId,
                profile.getNickname(),
                profile.getIntroduction(),
                profileImagePath,
                roundedScores,
                mostGenre,
                mostRoleList,
                evaluationDetails
        );
    }

    /**
     * Evaluation 엔티티를 EvaluationDetailDto로 변환
     */
    private EvaluationDetailDto convertToEvaluationDetailDto(Evaluation evaluation) {
        return new EvaluationDetailDto(
                evaluation.getEvaluator().getUserId(),
                evaluation.getEvaluatee().getUserId(),
                evaluation.getBoard().getBoardId(),
                evaluation.getComment(),
                evaluation.getScore1(),
                evaluation.getScore2(),
                evaluation.getScore3(),
                evaluation.getScore4(),
                evaluation.getScore5()
        );
    }
}
