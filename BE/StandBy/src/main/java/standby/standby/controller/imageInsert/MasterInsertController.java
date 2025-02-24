package standby.standby.controller.imageInsert;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/master")
public class MasterInsertController {
    // 포트폴리오 카드 생성
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> createPortfolioCard(
            @RequestPart(value = "name") String name,
            @RequestPart(value = "path") String path,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        Map<String, Object> map = new HashMap<>();
        String uploadDir = "/images";
        String oriname = file.getOriginalFilename();
        String imagePath = uploadDir + "/" + oriname;
        File dir = new File(uploadDir);
        boolean a = dir.mkdirs();
        File destFile = new File(imagePath);
        try {
            file.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("success", "저장성공");
        return ResponseEntity.ok(map);
    }
}