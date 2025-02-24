package standby.standby.jwt;//package standby.standby.jwt;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        // 실패 메시지 반환
//        String errorMessage = exception.getMessage(); // 예외 메시지 사용
//        response.getWriter().write(String.format("{\"error\": \"%s\"}", errorMessage));
//	}
//}
