
package standby.standby.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import standby.standby.dto.board.*;
import standby.standby.dto.boardCommon.ApplicationStatusDto;
import standby.standby.dto.boardCommon.CategorySearchDto;
import standby.standby.service.AttachementService;
import standby.standby.service.BoardService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;
    private final AttachementService attachementService;


    public BoardController(BoardService boardService, AttachementService attachementService) {
        this.boardService = boardService;
        this.attachementService = attachementService;
    }

    /* -------------------------*/
    /* -------------------------*/
    /*--------공통파트------------*/
    /* -------------------------*/
    /* -------------------------*/

    /* ----------------- */
    /* 리스트 전체 조회하기 */
    /* ---------------- */

    /* ----------------- */
    /* 리스트 검색 조회하기 */
    /* ---------------- */

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>>
    getBoardListTest() {
        List<BoardResponseDto> getBoardList = boardService.getBoardList(null, null);

        return ResponseEntity.ok(getBoardList);
    }


    @PostMapping
    public ResponseEntity<List<BoardResponseDto>> getBoardList(
            @RequestParam(required = false) List<String> keyword,
            @RequestBody(required = false) CategorySearchDto categorySearchDto
    ) {
        System.out.println("안녕하세요");
        if(keyword == null) {
            System.out.println("널입니다.");
        }
        else System.out.println(keyword.stream().toList());

        if(categorySearchDto == null) {
            System.out.println("널입니다.");
        }
        else {
            if(categorySearchDto.getCategorySearch() != null) {
                System.out.println(categorySearchDto.getCategorySearch().toString());
            }
            else System.out.println("널입니다.");
        }
        List<BoardResponseDto> getBoardList = boardService.getBoardList(keyword, categorySearchDto);
        //if(getBoardList == null || getBoardList.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        return ResponseEntity.ok(getBoardList);

    }



    /* ----------------- */
    /* 게시물 상세 조회하기 */
    /* ---------------- */
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDto> getBoardDetail(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.getBoardDetail(boardId);
        List<ApplicationStatusDto> applicantList = boardService.getBoardApplicantList(boardId);
        BoardDetailDto boardDetailDto = new BoardDetailDto(
                boardResponseDto,
                applicantList
        );

        return ResponseEntity.ok(boardDetailDto);
    }

    /* ------------------- */
    /* 게시물 지원자 조회하기 */
    /* ------------------ */
    @GetMapping("/{boardId}/applicantList")
    public ResponseEntity<List<ApplicationStatusDto>>
    getBoardApplicantList(@PathVariable Long boardId) {
        List<ApplicationStatusDto> applicationStatusDtoList = boardService.getBoardApplicantList(boardId);
        return ResponseEntity.ok(applicationStatusDtoList);//임시로둠
    }

    /* --------------------------------*/
    /* --------------------------------*/
    /*--------구인자 기능 파트------------*/
    /* --------------------------------*/
    /* --------------------------------*/

    /* ----------------- */
    /* 구인 게시글 작성하기 */
    /* ---------------- */
    @PostMapping(value = "/create",consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> createBoard(@RequestPart("board") BoardRequestDto boardRequestDto,
                                                           @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            // 파일 저장 로직 실행
            System.out.println("파일 업로드 처리 중...");
        } else {
            // 파일이 없을 때 처리
            System.out.println("업로드할 파일 없음");
        }

        boardService.createBoard(boardRequestDto, files);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Board created successfully");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 구인 게시글 수정하기 */
    /* ---------------- */
    @PutMapping(value = "/{boardId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> updateBoard( @PathVariable Long boardId,
                                               @RequestPart("board") BoardRequestDto boardRequestDto,
                                               @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        boardService.updateBoard(boardId, boardRequestDto, files);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Board updated successfully");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 구인 게시글 삭제하기 */
    /* ---------------- */

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Map<String, String>>
    deleteBoard(@PathVariable Long boardId) {


        boardService.deleteBoard(boardId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Board closed successfully");
        return ResponseEntity.ok(response);
    }

    /* ----------------- */
    /* 구인 게시글 마감하기 */
    /* ---------------- */


    @PatchMapping("/{boardId}/close")
    public ResponseEntity<Map<String, String>>
    closeBoard(@PathVariable Long boardId) {
        boardService.closeBoard(boardId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Board closed successfully");
        return ResponseEntity.ok(response);
    }


    /* ----------------- */
    /* 지원 구직자 거절하기 */
    /* ---------------- */
    @PatchMapping("/{boardId}/reject/{applicantId}")
    public ResponseEntity<Map<String, String>> rejectApplicant(@PathVariable Long boardId , @PathVariable Long applicantId){
        boardService.rejectApplicant(boardId, applicantId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant rejected");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 지원 구직자 면접수락 */
    /* ---------------- */
    @PatchMapping("/{boardId}/accept/{applicantId}")
    public ResponseEntity<Map<String, String>> acceptApplicant(@PathVariable Long boardId , @PathVariable Long applicantId){
        boardService.acceptApplicant(boardId, applicantId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant memorized");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 지원 구직자 메모하기 */
    /* ---------------- */
    @PostMapping("/{boardId}/applicant/{applicantId}/memos")
    public ResponseEntity<Map<String, String>>
    memorizeApplicant(@PathVariable Long boardId, @PathVariable Long applicantId,
                      @RequestBody BoardApplicationMemoRequestDto boardApplicationMemoRequestDto) {
        boardService.memorizeApplicant(boardId,applicantId , boardApplicationMemoRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant memorized");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 지원 구직자 합격결정 */
    /* ---------------- */
    @PatchMapping("/{boardId}/final/{applicantId}")
    public ResponseEntity<Map<String, String>> decideApplicant(@PathVariable Long boardId, @PathVariable Long applicantId){
        boardService.decideApplicant(boardId, applicantId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant decided");
        return ResponseEntity.ok(response);
    }
    /* --------------------------------*/
    /* --------------------------------*/
    /*--------구직자 기능 파트------------*/
    /* --------------------------------*/
    /* --------------------------------*/


    /* ----------------- */
    /* 구인 게시글 지원하기 */
    /* ---------------- */
    @PostMapping("/{boardId}/applicant/{applicantId}")
    public ResponseEntity<Map<String, String>> createApplicant
    (@PathVariable Long boardId,@PathVariable Long applicantId,
     @RequestBody BoardApplicationRequestDto boardApplicationRequestDto ) {
        boardService.createApplicant(boardId, applicantId, boardApplicationRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant created");
        return ResponseEntity.ok(response);
    }
    /* ----------------- */
    /* 구인 게시글 지원취소 */
    /* ---------------- */
    @DeleteMapping("/{boardId}/applicant/{applicantId}")
    public ResponseEntity<Map<String, String>> deleteApplicant(@PathVariable Long boardId, @PathVariable Long applicantId){
        boardService.deleteApplicant(boardId, applicantId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Applicant deleted");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/score")
    public ResponseEntity<Map<String, String>> createScore(@RequestBody BoardEvaluationRequestDto boardEvaluationRequestDto) {
        boardService.createScore(boardEvaluationRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Board score created successfully");
        return ResponseEntity.ok(response);
    }


    /*  다운로드 */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = attachementService.downloadAttachment(fileName);

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // 다운로드 응답
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/preview/{fileName}")
    public ResponseEntity<Resource> previewFile(@PathVariable String fileName) {
        Resource resource = attachementService.previewAttachment(fileName);

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(getMediaTypeForFileName(fileName)) // 미디어 타입 자동 설정
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType getMediaTypeForFileName(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        if (fileName.endsWith(".png")) return MediaType.IMAGE_PNG;
        if (fileName.endsWith(".gif")) return MediaType.IMAGE_GIF;
        if (fileName.endsWith(".pdf")) return MediaType.APPLICATION_PDF;
        return MediaType.APPLICATION_OCTET_STREAM;
    }



}
