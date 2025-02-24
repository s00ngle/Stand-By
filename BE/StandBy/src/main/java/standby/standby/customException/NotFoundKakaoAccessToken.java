package standby.standby.customException;

public class NotFoundKakaoAccessToken extends RuntimeException {
    public NotFoundKakaoAccessToken(String message) {
        super(message);
    }
}
