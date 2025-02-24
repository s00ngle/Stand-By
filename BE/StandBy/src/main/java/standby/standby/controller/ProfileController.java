package standby.standby.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import standby.standby.dto.profile.request.ProfileRequestDto;
import standby.standby.dto.profile.response.EvaluationResponseDto;
import standby.standby.dto.profile.response.ProfileResponseDto;
import standby.standby.service.EvaluationService;
import standby.standby.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final EvaluationService evaluationService;

    // userId 기반으로 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto profileResponse = profileService.getProfile(userId);

        if (profileResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(profileResponse);
    }


    //  userId 기반으로 프로필 수정
    @PutMapping(value = "/{userId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> updateProfile(
            @PathVariable Long userId,
            @RequestPart("profile") ProfileRequestDto profileRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile profileImage
    ) {
        System.out.println("🔹 [DEBUG] 받은 userId: " + userId);
        System.out.println("🔹 [DEBUG] 받은 ProfileRequestDto: " + profileRequestDto);
        System.out.println("🔹 [DEBUG] 받은 profileImage: " + (profileImage != null ? "이미지 있음" : "이미지 없음"));

        try {
            profileService.updateProfile(userId, profileRequestDto, profileImage);
            System.out.println(" [DEBUG] profileService.updateProfile 실행 완료");

            Map<String, String> response = new HashMap<>();
            response.put("message", "성공적으로 수정됨");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println(" [ERROR] 프로필 업데이트 중 오류 발생: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> response = new HashMap<>();
            response.put("error", "프로필 업데이트 실패: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/evaluations/{userId}")
    public ResponseEntity<EvaluationResponseDto> getEvaluation(@PathVariable Long userId){
        EvaluationResponseDto evaluationResponse = evaluationService.getEvaluation(userId);

        if (evaluationResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(evaluationResponse);
    }
}
