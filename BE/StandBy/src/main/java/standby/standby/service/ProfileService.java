package standby.standby.service;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import standby.standby.dto.profile.component.*;
import standby.standby.dto.profile.request.ProfileRequestDto;
import standby.standby.dto.profile.response.ProfileResponseDto;
import standby.standby.entity.*;
import standby.standby.enums.ApplicantStatus;
import standby.standby.repository.*;
import java.util.function.Function;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileImageRepository profileImageRepository;
    private final BoardRepository boardRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final EvaluationRepository evaluationRepository;
    private final CategoryBoardRepository categoryBoardRepository;

    ////////////// 프로필 조회 (구직자 & 구인자 통합) //////////////
    @Transactional
    public ProfileResponseDto getProfile(Long userId) {
        Profile profile = profileRepository.findById(userId).orElse(null);

        if (profile == null) {
            return null;
        }

        //  1. 구인자 & 구직자 공통 프로필
        ProfileComponentDto profileDto = convertToProfileComponentDto(profile);


        //  2. 구인자 - 현재 구인중인 게시글들 (status = true)
        List<BoardPostedComponentDto> activeBoards = boardRepository.findByAuthor_UserIdAndStatus(
                        profile.getUser().getUserId(), true)
                .stream()
                .map(this::convertToBoardComponentDto)
                .collect(Collectors.toList());

        //  3. 구인자 - 구인완료 된 글 (status = false)
        List<BoardPostedComponentDto> closedBoards = boardRepository.findByAuthor_UserIdAndStatus(  // 누락된 메서드명 추가
                        profile.getUser().getUserId(), false)
                .stream()
                .map(this::convertToBoardComponentDto)
                .collect(Collectors.toList());

        //  4. 구직자 - 지원한 게시글 조회 (평가 X)
        List<BoardAppliedComponentDto> appliedBoards = applicationStatusRepository.findByApplicantId(
                        profile.getUser().getUserId())
                .stream()
                .map(this::convertToBoardAppliedComponentDto)
                .collect(Collectors.toList());

        //  5. 구직자 - 최종 합격한 게시글 조회 (평가 O)
        List<BoardCareerComponentDto> careerBoards = applicationStatusRepository.findFinalAcceptedByApplicantId(
                        profile.getUser().getUserId(), ApplicantStatus.최종합격)
                .stream()
                .map(this::convertToBoardCareerComponentDto)
                .collect(Collectors.toList());

        //  6. 최종 프로필 반환 - 사용자 역할에 따라 다른 정보 반환
        return new ProfileResponseDto(
                profileDto,
                profile.getUser().getRole().equals("employee") ? appliedBoards : null,    // 지원자인 경우만 반환
                profile.getUser().getRole().equals("employee") ? careerBoards : null,     // 지원자인 경우만 반환
                profile.getUser().getRole().equals("employer") ? activeBoards : null,      // 구인자인 경우만 반환
                profile.getUser().getRole().equals("employer") ? closedBoards : null       // 구인자인 경우만 반환
        );
    }


    ////////////// 프로필 수정(구인자 & 구직자) //////////////
    @Transactional(rollbackFor = Exception.class)
    public String updateProfile(Long userId, ProfileRequestDto requestDto, MultipartFile profileImage) {
        Profile profile = profileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 ID에 대한 프로필을 찾을 수 없습니다."));

        User user = profile.getUser();

        if (!Objects.equals(user.getUserId(), requestDto.getUserId())) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다.");
        }

        boolean isProfileUpdated = false;

        // 1. 닉네임 변경 (닉네임 중복 검사 추가)
        if (!Objects.equals(profile.getNickname(), requestDto.getNickname())) {
            System.out.println(" 닉네임 변경됨: " + profile.getNickname() + " → " + requestDto.getNickname());
            profile.setNickname(requestDto.getNickname());
            isProfileUpdated = true;
        }

        // 2. 자기소개 변경
        if (!Objects.equals(profile.getIntroduction(), requestDto.getIntroduction())) {
            System.out.println(" 자기소개 변경됨: " + profile.getIntroduction() + " → " + requestDto.getIntroduction());
            profile.setIntroduction(requestDto.getIntroduction());
            isProfileUpdated = true;
        }

        // 3. 구직자(employee) 추가 정보
        if ("employee".equals(user.getRole())) {
            // 성별 변경
            if (!Objects.equals(profile.getGender(), requestDto.getGender())) {
                System.out.println(" 성별 변경됨: " + profile.getGender() + " → " + requestDto.getGender());
                profile.setGender(requestDto.getGender());
                isProfileUpdated = true;
            }

            // 키 변경
            if (!Objects.equals(profile.getHeight(), requestDto.getHeight())) {
                System.out.println(" 키 변경됨: " + profile.getHeight() + " → " + requestDto.getHeight());
                profile.setHeight(requestDto.getHeight());
                isProfileUpdated = true;
            }

            // 몸무게 변경
            if (!Objects.equals(profile.getWeight(), requestDto.getWeight())) {
                System.out.println(" 몸무게 변경됨: " + profile.getWeight() + " → " + requestDto.getWeight());
                profile.setWeight(requestDto.getWeight());
                isProfileUpdated = true;
            }

            // 작품 수 변경
            if (!Objects.equals(profile.getWorkCounts(), requestDto.getWorkCounts())) {
                System.out.println(" 작품수 변경됨: " + profile.getWorkCounts() + " → " + requestDto.getWorkCounts());
                profile.setWorkCounts(requestDto.getWorkCounts());
                isProfileUpdated = true;
            }

            // 경력 변경
            if (!Objects.equals(profile.getWorkYears(), requestDto.getWorkYears())) {
                System.out.println(" 경력 변경됨: " + profile.getWorkYears() + " → " + requestDto.getWorkYears());
                profile.setWorkYears(requestDto.getWorkYears());
                isProfileUpdated = true;
            }

            // 생년월일 변경
            if (!Objects.equals(profile.getBirthDate(), requestDto.getBirthDate())) {
                System.out.println(" 생년월일 변경됨: " + profile.getBirthDate() + " → " + requestDto.getBirthDate());
                profile.setBirthDate(requestDto.getBirthDate());
                isProfileUpdated = true;
            }
        }


        // 4. 프로필 이미지 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            System.out.println(" 새로운 프로필 이미지 등록 요청됨.");
            isProfileUpdated = true;

            // 기존 프로필 이미지 DB 레코드 삭제 (단, 기본 아바타(default-avatar)인 경우는 삭제하지 않음)
            List<ProfileImage> existingImages = profileImageRepository.findByProfile_ProfileId(profile.getProfileId());
            for (ProfileImage img : existingImages) {
                if (img != null && !img.getImagePath().equals("https://i12b211.p.ssafy.io/api/images/default-avatar.png")) {
                    profileImageRepository.delete(img);
                }
            }

            // 새로운 프로필 이미지 저장
            ProfileImage newProfileImage = new ProfileImage();
            saveProfileImage(newProfileImage, profileImage, profile.getProfileId());

            // 저장 확인
            Optional<ProfileImage> savedImage = profileImageRepository.findByProfile_ProfileId(profile.getProfileId())
                    .stream()
                    .findFirst();
            if (savedImage.isPresent()) {
                System.out.println(" 새로운 프로필 이미지 저장 완료: " + savedImage.get().getImagePath());
            } else {
                throw new RuntimeException(" 새로운 프로필 이미지 저장 실패!");
            }
        }


        // 변경된 정보가 없으면 업데이트 실행 안 함
        if (!isProfileUpdated) {
            System.out.println(" 변경된 정보가 없습니다. 업데이트를 수행하지 않음.");
            throw new IllegalArgumentException("변경된 정보가 없습니다.");
        }

        // 변경된 정보가 있을 경우만 저장
        try {
            profileRepository.save(profile);
            System.out.println(" 프로필 업데이트 성공!");
        } catch (Exception e) {
            System.out.println(" 프로필 업데이트 실패: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("프로필 업데이트 중 오류 발생", e);
        }
        return user.getRole();
    }


    ////////////// 프로필 사진 저장 //////////////
    private void saveProfileImage(ProfileImage profileImage, MultipartFile file, Long profileId) {
      String uploadDir = "/images/profileImages"; // 배포 환경 경로
//        String uploadDir = "c:/images/profileImages"; // 로컬 환경

        // 기존 프로필 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다. (ID: " + profileId + ")"));


//        // 기존 이미지 삭제 (기본 아바타 이미지 제외)
//        if (profileImage.getImagePath() != null &&
//                !profileImage.getImagePath().equals("https://i12b211.p.ssafy.io/api/images/default-avatar.png")) {
//
//            File deleteFile = new File(profileImage.getImagePath());
//            if (deleteFile.exists()) {
//                boolean isDelete = deleteFile.delete();
//                System.out.println("🔹 기존 이미지 삭제됨: " + (isDelete ? "성공" : "실패"));
//            }
//        }


        if (file != null && !file.isEmpty()) {
            try {
                // 파일 이름 생성
                String oriname = file.getOriginalFilename();
                String systemName = UUID.randomUUID().toString() + "_" + oriname;
                String imagePath = uploadDir + "/" + systemName;

                System.out.println(" [DEBUG] 원본 파일 이름: " + oriname);
                System.out.println(" [DEBUG] 저장될 파일 경로: " + imagePath);

                // 저장 폴더 생성
                File dir = new File(uploadDir);
                boolean isDirCreated = dir.mkdirs();

                // 실제 파일객체 생성
                File destFile = new File(imagePath);
                file.transferTo(destFile);

                // 파일 저장 완료 후 DB 업데이트
                profileImage.setProfile(profile);
                profileImage.setImagePath(imagePath);
                profileImage.setOriName(oriname);
                profileImage.setSystemName(systemName);
                profileImage.setCreatedAt(LocalDateTime.now());

                profileImageRepository.save(profileImage);
                System.out.println(" [DEBUG] 프로필 이미지 DB 저장 완료");

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("프로필 이미지 저장 중 오류 발생", e);
            }
        } else {
            System.out.println(" [DEBUG] 파일이 비어 있음. 저장하지 않음.");
        }
    }


    // ProfileComponentDto 변환 메서드
    private ProfileComponentDto convertToProfileComponentDto(Profile profile) {
        //  빈 문자열을 `null`로 변환하는 함수 추가
        Function<String, String> emptyToNull = str -> (str == null || str.isEmpty()) ? null : str;

        // 1. 모든 지원 내역 조회
        List<ApplicationStatus> allApplications = applicationStatusRepository.findByApplicantId(profile.getUser().getUserId());

        // 2. 평가 점수 가져오기 (평균이 없으면 0.0으로 초기화)
        List<Object[]> avgScoresList = evaluationRepository.getAverageScoresOnlyFromEvaluation(profile.getUser().getUserId()).orElse(Collections.emptyList());

        // 3. 기본값 설정
        Double[] scores = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};

        // 4. 리스트가 비어있지 않다면 첫 번째 요소 사용
        if (!avgScoresList.isEmpty() && avgScoresList.get(0) != null) {
            Object[] result = avgScoresList.get(0);

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

        // 6. 구인자와 구직자 모스트 장르 1개 및 역할 최대 3개 가져오기
        List<String> topCategories = profile.getUser().getRole().equals("employer")
                ? categoryBoardRepository.findTopCategoriesByEmployer(profile.getUser().getUserId())
                : categoryBoardRepository.findTopCategoriesByApplicant(profile.getUser().getUserId());

        if (topCategories == null) {
            topCategories = Collections.emptyList();
        }

        long recruitingCount = boardRepository.countByAuthor_UserId(profile.getUser().getUserId());

        // 7. 프로필 이미지 가져오기
        String profileImagePath = null;
        if (profile.getProfileImages() != null && !profile.getProfileImages().isEmpty()) {
            ProfileImage latestImage = profile.getProfileImages().stream()
                    .max(Comparator.comparing(ProfileImage::getCreatedAt))
                    .get();
            profileImagePath = latestImage.getImagePath();

            // 이미지 경로가 상대 경로이면 기본 URL을 붙여 절대 경로로 변환
            if (profileImagePath != null && !profileImagePath.startsWith("http")) {
                profileImagePath = "https://i12b211.p.ssafy.io/api" + profileImagePath;
            }
        }

        // 8. 장르 및 역할 분리
        String mostGenre = !topCategories.isEmpty() ? topCategories.get(0) : null;
        List<String> mostRoleList = (topCategories.size() > 1)
                ? topCategories.subList(1, Math.min(4, topCategories.size()))
                : Collections.emptyList();

        // ⃣ 최종 DTO 생성 (빈 문자열을 `null`로 변환 후 전달)
        return new ProfileComponentDto(
                profile.getUser().getRole(),
                profile.getUser().getUserId(),
                emptyToNull.apply(profile.getNickname()),       // 닉네임 빈 문자열 → null
                emptyToNull.apply(profile.getIntroduction()),   // 자기소개 빈 문자열 → null
                profile.getBirthDate(),
                emptyToNull.apply(profile.getGender()),         // 성별 빈 문자열 → null
                profile.getHeight(),
                profile.getWeight(),
                roundedScores,
                emptyToNull.apply(profile.getWorkCounts()),     // 작품 수 빈 문자열 → null
                emptyToNull.apply(profile.getWorkYears()),      // 경력 빈 문자열 → null
                profileImagePath,
                (int) recruitingCount,
                mostGenre,
                mostRoleList
        );
    }



    private BoardAppliedComponentDto convertToBoardAppliedComponentDto(ApplicationStatus status) {
        Board board = status.getBoard();
        return new BoardAppliedComponentDto(
                board.getBoardId(),
                board.getAuthor().getUserId(),
                status.getApplicant().getUserId(),
                status.getApplicantStatus(),
                board.getTitle(),
                board.getStartDate(),
                board.getEndDate(),
                board.getStatus()
        );
    }


    private BoardCareerComponentDto convertToBoardCareerComponentDto(ApplicationStatus status) {
        Board board = status.getBoard();

        // "최종합격"이 아니면 평가 정보 없이 반환
        if (!ApplicantStatus.최종합격.equals(status.getApplicantStatus())) {
            return new BoardCareerComponentDto(
                    board.getBoardId(),
                    board.getAuthor().getUserId(),
                    status.getApplicant().getUserId(),
                    status.getApplicantStatus(),
                    board.getTitle(),
                    board.getStartDate(),
                    board.getEndDate(),
                    board.getStatus(),
                    null, null, null, null, null, // 평가 정보 없음
                    null
            );
        }

        //  "최종합격"이면 평가 조회 (없으면 NULL 처리)
        Evaluation evaluation = evaluationRepository.findByBoard_BoardIdAndEvaluatee_UserId(
                board.getBoardId(), status.getApplicant().getUserId()
        );

        return new BoardCareerComponentDto(
                board.getBoardId(),
                board.getAuthor().getUserId(),
                status.getApplicant().getUserId(),
                status.getApplicantStatus(),
                board.getTitle(),
                board.getStartDate(),
                board.getEndDate(),
                board.getStatus(),
                evaluation != null ? Math.round(evaluation.getScore1() * 10.0) / 10.0 : null,
                evaluation != null ? Math.round(evaluation.getScore2() * 10.0) / 10.0 : null,
                evaluation != null ? Math.round(evaluation.getScore3() * 10.0) / 10.0 : null,
                evaluation != null ? Math.round(evaluation.getScore4() * 10.0) / 10.0 : null,
                evaluation != null ? Math.round(evaluation.getScore5() * 10.0) / 10.0 : null,
                evaluation != null ? evaluation.getComment() : null
        );
    }

    private BoardPostedComponentDto convertToBoardComponentDto(Board board) {
        return new BoardPostedComponentDto(
                board.getBoardId(),
                board.getAuthor().getUserId(),
                board.getAuthor().getProfile().getNickname(),
                board.getTitle(),
                board.getStartDate(),
                board.getEndDate(),
                board.getStatus()
        );
    }
}
