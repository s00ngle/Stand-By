package standby.standby.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import standby.standby.dto.portfolio.component.PortfolioCardComponentDto;
import standby.standby.dto.portfolio.request.PortfolioCardRequestDto;
import standby.standby.dto.portfolio.response.PortfolioCardResponseDto;
import standby.standby.dto.portfolio.response.CardImageResponseDto;
import standby.standby.entity.PortfolioCard;
import standby.standby.entity.PortfolioCardImage;
import standby.standby.repository.user.UserRepository;
import standby.standby.service.PortfolioService;
import standby.standby.service.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final UserService userService;
    private final PortfolioService portfolioService;
    private final ObjectMapper objectMapper;

    // 프로필의 포트폴리오 전체 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getPortfolioCards(@PathVariable(name = "userId") Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<PortfolioCard> cards = portfolioService.getPortfolioCards(userId);
            List<PortfolioCardResponseDto> portfolioCards = new ArrayList<>();
            if (cards == null) {
                response.put("success", true);
                response.put("message", "포트폴리오 조회 성공");
                response.put("cards", null);
                return ResponseEntity.ok(response);
            }
            for (PortfolioCard card : cards) {
                portfolioCards.add(new PortfolioCardResponseDto(card));
            }
            response.put("success", true);
            response.put("message", "포트폴리오 조회 성공");
            response.put("cards", portfolioCards);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", "false");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 포트폴리오 카드 생성
    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> createPortfolioCard(
            @PathVariable(name = "userId") Long userId,
            @RequestPart(value = "card") String cardInfoJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {

        Map<String, Object> response = new HashMap<>();
        try {
            PortfolioCardRequestDto cardInfo = objectMapper.readValue(cardInfoJson, PortfolioCardRequestDto.class);
            PortfolioCard card = portfolioService.createPortfolioCard(userId, cardInfo, files);

            List<PortfolioCardResponseDto> portfolioCards = new ArrayList<>();
            portfolioCards.add(new PortfolioCardResponseDto(card));

            response.put("success", true);
            response.put("message", "포트폴리오 카드 생성 성공");
            response.put("cards", portfolioCards);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 포트폴리오 카드 수정
    @PutMapping("/card/{cardId}")
    public ResponseEntity<Map<String, Object>> updatePortfolioCard(
            @PathVariable(value = "cardId") Long cardId,
            @RequestPart(value = "card") String cardInfoJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            PortfolioCardRequestDto cardInfo = objectMapper.readValue(cardInfoJson, PortfolioCardRequestDto.class);
            PortfolioCard card = portfolioService.updatePortfolioCard(cardId, cardInfo, files);

            List<PortfolioCardResponseDto> portfolioCards = new ArrayList<>();
            portfolioCards.add(new PortfolioCardResponseDto(card));

            response.put("message", "포트폴리오 카드 수정 성공");
            response.put("cards", portfolioCards);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 포트폴리오 카드 삭제
    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<Map<String, Object>> deletePortfolioCard(
            @PathVariable(name = "cardId") Long cardId
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            portfolioService.deletePortfolioCard(cardId);
            response.put("success", true);
            response.put("message", "포트폴리오 카드 삭제 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}