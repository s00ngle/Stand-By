package standby.standby.customException;

public class NotFoundUserId extends RuntimeException {
    public NotFoundUserId(String message) {
        super(message);
    }
}
