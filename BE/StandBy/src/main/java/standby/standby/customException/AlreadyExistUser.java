package standby.standby.customException;

public class AlreadyExistUser extends RuntimeException {
    public AlreadyExistUser(String message) {
        super(message);
    }
}
