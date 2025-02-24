package standby.standby.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// 특정 메서드에만 적용
@RestController
@RequestMapping("/api/test")
public class TestController {

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Map<String, String>> getTestData() {
        Map<String, String> response = new HashMap<>();
        response.put("value", "test");
        return ResponseEntity.ok(response);
    }
    
}
