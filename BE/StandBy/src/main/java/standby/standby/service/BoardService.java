package standby.standby.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import standby.standby.dto.board.*;
import standby.standby.dto.boardCommon.*;
import standby.standby.entity.*;
import standby.standby.enums.ApplicantStatus;
import standby.standby.repository.*;
import standby.standby.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BoardService {
    private final UserRepository userRepository;
    //private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;
    private final WorkplaceRepository workplaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final EvaluationRepository evaluationRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBoardRepository categoryBoardRepository;
    private final AttachementService attachementService;
    private final WorkplaceService workplaceService;
    private final CategoryBoardService categoryBoardService;
    private final CategoryApplicationRepository categoryApplicationRepository;
    private final ProfileRepository profileRepository;
    private final ProfileImageRepository profileImageRepository;

    public BoardService(UserRepository userRepository, BoardRepository boardRepository, WorkplaceRepository workplaceRepository, AttachmentRepository attachmentRepository, ApplicationStatusRepository applicationStatusRepository, EvaluationRepository evaluationRepository, CategoryRepository categoryRepository, CategoryBoardRepository categoryBoardRepository, AttachementService attachementService, WorkplaceService workplaceService, CategoryBoardService categoryBoardService, CategoryApplicationRepository categoryApplicationRepository, ProfileRepository profileRepository, ProfileImageRepository profileImageRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.workplaceRepository = workplaceRepository;
        this.attachmentRepository = attachmentRepository;
        this.applicationStatusRepository = applicationStatusRepository;
        this.evaluationRepository = evaluationRepository;
        this.categoryRepository = categoryRepository;
        this.categoryBoardRepository = categoryBoardRepository;
        this.attachementService = attachementService;
        this.workplaceService = workplaceService;
        this.categoryBoardService = categoryBoardService;
        this.categoryApplicationRepository = categoryApplicationRepository;
        this.profileRepository = profileRepository;
        this.profileImageRepository = profileImageRepository;
    }

    /* 게시글 리스트 조회 */
    public List<BoardResponseDto> getBoardList (List<String> keywordList, CategorySearchDto categorySearchDto) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 리스트 서비스입니다.");
        List<String> keywords = (keywordList == null || keywordList.isEmpty()) ? null
                : keywordList.stream().map(String::toLowerCase).toList();
        System.out.println("키워드 리스트 추출");
        if(keywords != null) {
            System.out.println(keywords.toString());
        }
        else System.out.println("null");

        // 카테고리 리스트 추출
        List<String> categories = (categorySearchDto == null
                ||categorySearchDto.getCategorySearch() == null || categorySearchDto.getCategorySearch().isEmpty()) ? null
                : categorySearchDto.getCategorySearch();
        System.out.println("카테고리 리스트 추출");
        if (categories != null) {
            System.out.println(categories.toString());
        }
        else System.out.println("null");
        // 키워드 또는 카테고리 중 하나라도 일치하는 게시글 검색
        List<Board> boardList=null;
        if ((keywords == null || keywords.isEmpty()) && (categories == null || categories.isEmpty() )) {
            boardList = boardRepository.findAllBoards(); // 전체 조회
        } else {

            boardList =boardRepository.searchBoardsByKeywordsAndCategories(keywords, categories);
        }
        System.out.println("보드리스트 확인입니다.3");
        if(boardList != null) {
            System.out.println(boardList.toString());
        }
        else System.out.println("null");
        if(boardList == null || boardList.isEmpty()) { return null; }

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardResponseDto boardResponseDto = getBoardDetail(board.getBoardId());
            if(boardResponseDto != null) {
                System.out.println(boardResponseDto.toString());
            }
            else System.out.println("null");
            boardResponseDtoList.add(boardResponseDto);
        }
        System.out.println("보드DTO학인인입니다..4");
        if(boardResponseDtoList != null) {
            System.out.println(boardResponseDtoList.toString());
        }
        else System.out.println("null");
        return boardResponseDtoList;
    }

    /* 게시글 상세 조회 */
    public BoardResponseDto getBoardDetail(Long boardId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 상세보기 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Optional<Profile> profile = profileRepository.findByUser_UserId(board.getAuthor().getUserId());
        Optional<ProfileImage> profileImage = Optional.of(profileImageRepository.findById(profile.get().getProfileId())
                .orElse(new ProfileImage(null, null, null, null, null, null, null, null)));
        List<CategoryBoard> categoryBoardList = categoryBoardRepository.findByBoard(board);

        String genre = null;
        List<String> roles = new ArrayList<>();
        // if(categoryBoardList != null && !categoryBoardList.isEmpty()) {
        //     for (CategoryBoard categoryBoard : categoryBoardList) {
        //         if (categoryBoard.getCategory().getIsGenre()) genre = categoryBoard.getCategory().getCategoryName();
        //         else roles.add(categoryBoard.getCategory().getCategoryName());
        //     }
        // }
                if (categoryBoardList != null && !categoryBoardList.isEmpty()) {
            for (CategoryBoard categoryBoard : categoryBoardList) {
                if (categoryBoard.getCategory() == null) continue; // category가 null이면 다음 루프로 이동

                // category.getIsGenre()가 null이면 기본값 false로 설정
                if (categoryBoard.getCategory().getIsGenre() == null) {
                    categoryBoard.getCategory().setIsGenre(false);
                }

                // isGenre 여부에 따라 분류
                if (categoryBoard.getCategory().getIsGenre()) {
                    genre = categoryBoard.getCategory().getCategoryName();
                } else {
                    roles.add(categoryBoard.getCategory().getCategoryName());
                }
            }
        }
            //로케이션 부분
            List<Workplace> workplaceList = workplaceRepository.findByBoard(board);
            List<LocationDto> location = workplaceList.stream()
                    .map(workplace -> new LocationDto(
                            workplace.getPlaceName(),
                            workplace.getLongitude(),
                            workplace.getLatitude()
                    )).collect(Collectors.toList());

            // 이미지부분
            List<Attachment> attachmentList = attachmentRepository.findByBoard(board);
            List<String> images = new ArrayList<>();
            for (Attachment attachment : attachmentList) {
                String fileUrl = "https://i12b211.p.ssafy.io/api" + attachment.getFilePath();
                images.add(fileUrl);
            }

            //날짜 부분
            DateDto date = new DateDto(
                    board.getStartDate().toString(),
                    board.getEndDate().toString());

            boolean end = false;
            if(board.getDeletedAt() != null) {
                end = true;
            }

            return new BoardResponseDto(
                    board.getBoardId(),
                    profile.get().getNickname(),
                    profileImage.get().getImagePath(),
                    board.getTitle(),
                    board.getContent(),
                    genre,
                    roles,
                    location,
                    date,
                    board.getPay(),
                    board.getPayType(),
                    board.getDuration(),
                    board.getAvailablePositions(),
                    images,
                    board.getStatus(),
                    end,
                    board.getAuthor().getUserId()



            );

        }


    /* 지원자 목록 조회 */
    public List<ApplicationStatusDto> getBoardApplicantList(Long boardId) {
        // TODO: 구현
        System.out.println("안녕하세요 지원자 목록 조회 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        List<ApplicationStatus> applicationStatusList = applicationStatusRepository.findByBoardAndDeletedAtIsNull(board);

        List<ApplicationStatus> filteredList = applicationStatusList.stream()
                .filter(applicationStatus -> applicationStatus.getApplicant().getDeletedAt() == null)
                .collect(Collectors.toList());

        applicationStatusList = filteredList;

        List<ApplicationStatusDto> applicationStatusDtoList = applicationStatusList.stream()
                .map(applicationStatus -> new ApplicationStatusDto(
                        applicationStatus.getApplicant().getUserId(),
                        applicationStatus.getEmployer().getUserId(),
                        applicationStatus.getBoard().getBoardId(),
                        applicationStatus.getApplicantStatus().toString(),
                        applicationStatus.getMemo(),
                        applicationStatus.getUpdatedAt().toString(),
                        null,
                        null,
                        null

                )).collect(Collectors.toList());

        for (ApplicationStatusDto applicationStatusDto : applicationStatusDtoList) {
            User applicant = userRepository.findById(applicationStatusDto.getApplicantId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            ApplicationStatus applicationStatus = applicationStatusRepository.findByBoardAndApplicant(board, applicant);
            CategoryApplication categoryApplication = categoryApplicationRepository.findByApplicationStatus(applicationStatus);
            if(categoryApplication != null) {
                String role = categoryApplication.getCategory().getCategoryName();
                applicationStatusDto.setRole(role);
            }
            Optional<Profile> profile = profileRepository.findByUser_UserId(applicationStatusDto.getApplicantId());
            Optional<ProfileImage> profileImage = Optional.of(profileImageRepository.findById(profile.get().getProfileId())
                    .orElse(new ProfileImage(null, null, "이미지를 발견하지 못했습니다.", null, null, null, null, null)));
            applicationStatusDto.setProfileImage(profileImage.get().getImagePath());
            applicationStatusDto.setNickname(profile.get().getNickname());
        }

        return applicationStatusDtoList;
    }

    /* 게시글 생성 */
    @Transactional
    public void createBoard(BoardRequestDto requestDto, List<MultipartFile> files) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 생성 서비스입니다.");
        User user = userRepository.findById(requestDto.getAuthorId()).
                orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        LocalDate startTime = LocalDate.parse(requestDto.getStartDate());
        LocalDate endTime = LocalDate.parse(requestDto.getEndDate());

        // 보드 저장
        Board board = new Board();
        board.setAuthor(user);
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setStartDate(startTime);
        board.setEndDate(endTime);
        board.setPayType(requestDto.getPayType());
        board.setPay(requestDto.getPay());
        board.setDuration(requestDto.getDuration());
        board.setStatus(requestDto.isStatus());
        board.setAvailablePositions(requestDto.getAvailablePositions());
        boardRepository.save(board);

        // 기타 정보 저장
        workplaceService.createWorkplace(board,requestDto);
        attachementService.CreateAttachment(board,files);
        categoryBoardService.CreateCategory(board,requestDto);
    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(Long boardId, BoardRequestDto requestDto, List<MultipartFile> files) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 수정 서비스입니다.");
        /* 게시글 수정*/
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));
        User user = userRepository.findById(requestDto.getAuthorId()).
                orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        LocalDate startTime = LocalDate.parse(requestDto.getStartDate());
        LocalDate endTime = LocalDate.parse(requestDto.getEndDate());

        board.setAuthor(user);
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setStartDate(startTime);
        board.setEndDate(endTime);
        board.setPayType(requestDto.getPayType());
        board.setPay(requestDto.getPay());
        board.setStatus(requestDto.isStatus());
        board.setUpdatedAt(LocalDateTime.now());
        boardRepository.save(board);

        /* 일하는장소 수정*/
        workplaceRepository.deleteByBoard(board);
        workplaceService.createWorkplace(board,requestDto);


        /* 첨부파일 수정*/
        attachementService.deleteAttachment(board); // 파일삭제
        attachementService.CreateAttachment( board, files);

        /* 카테고리 수정*/
        categoryBoardRepository.deleteByBoard(board);
        categoryBoardService.CreateCategory(board, requestDto);
    }

    /* 게시글 삭제 */
    @Transactional
    public void deleteBoard(Long boardId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 삭제 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));

        // 보드 삭제
        board.setDeletedAt(LocalDateTime.now());

        // 데이터베이스에서 요소 삭제
        workplaceRepository.deleteByBoard(board);
        attachementService.deleteAttachment(board);
        categoryBoardRepository.deleteByBoard(board);
        List<ApplicationStatus> applicationStatusList = applicationStatusRepository.findByBoard(board);
        for (ApplicationStatus applicationStatus : applicationStatusList) {
            decideApplicant(board.getBoardId(), applicationStatus.getApplicant().getUserId());
        }
    }

    /* 게시글 마감 */
    @Transactional
    public void closeBoard(Long boardId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 마감 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));
        board.setStatus(false);
        board.setUpdatedAt(LocalDateTime.now());
        boardRepository.save(board);
        //마감되고 최종허락 아닌 인간들은 전부 마감 처리하는게 좋을까 ?
    }



    /* 지원자 거절 */
    @Transactional
    public BoardApplicationStatusResponseDto rejectApplicant(Long boardId, Long applicantId) {
        // TODO: 구현
        // 거절하면 어떻게 처리?
        System.out.println("안녕하세요 게시물 지원저를 거부하는 서비스입니다.");
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(applicantId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ApplicationStatus applicantStatus = applicationStatusRepository.findByBoardAndApplicant(board,user);
        applicantStatus.setApplicantStatus(ApplicantStatus.거절);
        applicationStatusRepository.save(applicantStatus);
        return null;
    }

    /* 지원자 수락 */
    @Transactional
    public BoardApplicationStatusResponseDto acceptApplicant(Long boardId, Long applicantId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 지원자를 수락하는 서비스입니다.");
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(applicantId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ApplicationStatus applicantStatus = applicationStatusRepository.findByBoardAndApplicant(board,user);
        applicantStatus.setApplicantStatus(ApplicantStatus.면접합격);
        applicationStatusRepository.save(applicantStatus);

        return null;

    }

    /* 지원자 메모 */
    @Transactional
    public String memorizeApplicant(Long boardId, Long applicantId, BoardApplicationMemoRequestDto requestDto) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 지원자에 대한 메모 서비스입니다.");
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(applicantId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ApplicationStatus applicantStatus = applicationStatusRepository.findByBoardAndApplicant(board,user);
        applicantStatus.setMemo(requestDto.getMemos());
        applicationStatusRepository.save(applicantStatus);
        /// /////////////////

        return null;
    }

    /* 지원자 최종 결정 */
    @Transactional
    public BoardApplicationStatusResponseDto decideApplicant(Long boardId, Long applicantId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 지원에 대한 최종 결정을 하는 서비스입니다.");
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(applicantId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ApplicationStatus applicantStatus = applicationStatusRepository.findByBoardAndApplicant(board,user);
        int beforeNumber = board.getAvailablePositions();
        int newNumber = beforeNumber - 1;
        if (newNumber < 0) {
            //에러던지기
            return null;
        }
        board.setAvailablePositions(newNumber);
        applicantStatus.setApplicantStatus(ApplicantStatus.최종합격);
        if(newNumber == 0) {
            closeBoard(board.getBoardId());
        }
        return null;
    }

    /* 게시글 지원 */
    @Transactional
    public void createApplicant(Long boardId, Long applicantId, BoardApplicationRequestDto requestDto) {
        // TODO: 구현
        // 보드게시판
        System.out.println("안녕하세요 게시물 지원신청을 하는 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        //작성자 유저아이디가  고용자 유저 아이디.
        User employer = userRepository.findById(board.getAuthor().getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 지원자 유저아이디
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // applicantsatus Entity 만들기



        ApplicationStatus applicationStatus = new ApplicationStatus(
                null
                ,employer   // 구인자
                ,applicant  // 지원자
                ,board // 게시판번호
                ,ApplicantStatus.지원완료
                ,null
                ,null
                ,LocalDateTime.now()
                ,null
        );

        applicationStatusRepository.save(applicationStatus);
        ApplicationStatus applicantStatus = applicationStatusRepository.findByBoardAndApplicant(board,applicant);

            Category category =  categoryRepository.findByCategoryName(requestDto.getRole());
            CategoryApplication categoryApplication = new CategoryApplication(
                    null,
                    applicationStatus,
                    category
            );

            categoryApplicationRepository.save(categoryApplication);
        }



    /* 지원 취소 */
    @Transactional
    public void deleteApplicant(Long boardId, Long applicantId) {
        // TODO: 구현
        System.out.println("안녕하세요 게시물 지원신청을 취소하는 서비스입니다.");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        //작성자 유저아이디가  고용자 유저 아이디.
        User employer = userRepository.findById(board.getAuthor().getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 지원자 유저아이디
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ApplicationStatus applicationStatus = applicationStatusRepository.findByBoardAndApplicant(board,applicant);

        //applicationStatus.setDeletedAt(LocalDateTime.now());
        applicationStatusRepository.deleteByBoardAndApplicant(board,applicant);
        categoryApplicationRepository.deleteByApplicationStatus(applicationStatus);
    }

    @Transactional
    public void createScore(BoardEvaluationRequestDto requestDto){
        Board board = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        //작성자 유저아이디가  고용자 유저 아이디.
        User evaluator = userRepository.findById(requestDto.getEvaluateeId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 지원자 유저아이디
        User evaluatee = userRepository.findById(requestDto.getEvaluateeId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Evaluation check = evaluationRepository.findByEvaluatorAndEvaluateeAndBoard(evaluator,evaluatee,board);
        if(check == null) {
            Evaluation evaluation = new Evaluation();
            evaluation.setEvaluator(evaluator);
            evaluation.setEvaluatee(evaluatee);
            evaluation.setBoard(board);
            evaluation.setComment(requestDto.getComment());
            evaluation.setScore1(requestDto.getScore1());
            evaluation.setScore2(requestDto.getScore2());
            evaluation.setScore3(requestDto.getScore3());
            evaluation.setScore4(requestDto.getScore4());
            evaluation.setScore5(requestDto.getScore5());
            evaluationRepository.save(evaluation);
        }else{
            if(check.getEvaluatee().equals(evaluator)&&check.getEvaluatee().equals(evaluatee)&&check.getBoard().equals(board)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 평가된 사람입니다.");
            }
        }


    }


}
