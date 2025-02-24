package standby.standby.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/sse")
public class SseController {
    private final SseEmitters sseEmitters;

    public SseController(SseEmitters sseEmitters) {
        this.sseEmitters = sseEmitters;
    }

    @GetMapping(value = "/connect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@PathVariable Long userId) {
        SseEmitter emitter = new SseEmitter(300 * 1000L);
        sseEmitters.add(userId, emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }

    @PostMapping("/notify/deny/{userId}/{boardId}")
    public ResponseEntity<Void> denyUser(@PathVariable Long userId, @PathVariable Long boardId) {
        String message = "거절됐습니다.";
        sseEmitters.sendNotification(userId, boardId, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notify/accept/{userId}/{boardId}")
    public ResponseEntity<Void> acceptUser(@PathVariable Long userId, @PathVariable Long boardId) {
        String message = "합격했습니다.";
        sseEmitters.sendNotification(userId, boardId, message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notify/apply/{userId}/{boardId}")
    public ResponseEntity<Void> applyUser(@PathVariable Long userId, @PathVariable Long boardId) {
        String message = "지원했습니다.";
        sseEmitters.sendNotification(userId, boardId, message);
        return ResponseEntity.ok().build();
    }

}