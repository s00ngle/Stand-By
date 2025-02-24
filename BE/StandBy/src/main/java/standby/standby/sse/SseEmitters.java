package standby.standby.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitters {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(Long userId, SseEmitter emitter) {
        this.emitters.put(userId, emitter);
        log.info("New emitter added for user {}: {}", userId, emitter);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback for user {}", userId);
            this.emitters.remove(userId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback for user {}", userId);
            emitter.complete();
            this.emitters.remove(userId);
        });

        return emitter;
    }

    public void sendNotification(Long userId, Long boardId, String message) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                // JSON 형태로 데이터 구성
                Map<String, Object> data = new HashMap<>();
                data.put("userId", userId);
                data.put("boardId", boardId);
                data.put("message", message);

                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(data));  // JSON 형태로 전송
            } catch (IOException e) {
                log.warn("Client {} disconnected, removing emitter.", userId);
                this.emitters.remove(userId);
            }
        }
    }
}