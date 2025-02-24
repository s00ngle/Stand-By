package standby.standby.jwt;//package standby.standby.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.StreamUtils;
//import standby.standby.dto.login.LoginDto;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Collection;
//import java.util.Iterator;
//
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//
//	private final AuthenticationManager authenticationManager;
//	private final JWTUtil jwtUtil;
//
//	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
//		this.authenticationManager = authenticationManager;
//		this.jwtUtil = jwtUtil;
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//
//		LoginDto loginDTO = new LoginDto();
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			ServletInputStream inputStream = request.getInputStream();
//			String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//			loginDTO = objectMapper.readValue(messageBody, LoginDto.class);
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		String email = loginDTO.getEmail();
//		String password = loginDTO.getPassword();
//
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password,
//				null);
//
//		return authenticationManager.authenticate(authToken);
//	}
//
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authentication) {
//
//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//
//		String email = customUserDetails.getUsername();
//
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//		GrantedAuthority auth = iterator.next();
//
//		String role = auth.getAuthority();
//
//
//		String token = jwtUtil.createJwt(email, role, 1000 * 60 * 60 * 10L);
//
//		// JWT를 Authorization 헤더에 추가
//		response.addHeader("authorization", "Bearer " + token);
//	}
//
//	@Override
//	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException failed) {
//
//		response.setStatus(401);
//	}
//}
