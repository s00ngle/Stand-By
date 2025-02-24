package standby.standby.customException;

public class NotFoundKakaoUserInfo extends RuntimeException {
  public NotFoundKakaoUserInfo(String message) {
    super(message);
  }
}
