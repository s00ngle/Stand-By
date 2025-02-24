package standby.standby.exception;

public class ProfileExceptionMessages {
    // 🔹 공통 예외 메시지
    public static final String ENTITY_NOT_FOUND = "%s ID(%d)를 찾을 수 없습니다.";
    public static final String VALIDATION_FAILED = "입력값이 잘못되었습니다: %s";

    // 🔹 프로필 관련 예외
    public static final String PROFILE_NOT_FOUND = "해당 ID(%d)의 프로필을 찾을 수 없습니다.";

    // 🔹 게시글 관련 예외
    public static final String BOARD_NOT_FOUND = "해당 ID(%d)의 게시글을 찾을 수 없습니다.";

    // 🔹 지원 상태 관련 예외
    public static final String APPLICATION_NOT_FOUND = "해당 ID(%d)의 지원 정보를 찾을 수 없습니다.";
    public static final String APPLICATION_ACCESS_DENIED = "이 지원 정보를 조회할 권한이 없습니다.";

    // 🔹 사용자 관련 예외
    public static final String USER_NOT_FOUND = "해당 ID(%d)의 사용자를 찾을 수 없습니다.";
    public static final String UNAUTHORIZED_ACCESS = "접근 권한이 없습니다.";
}
