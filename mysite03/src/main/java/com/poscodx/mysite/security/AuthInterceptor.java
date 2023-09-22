package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1.handler 종류 확인
		if (!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}

		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. Handler Method의 @Auth 가져오기
		// 개별 컨트롤러
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
//		이 코드는 현재 처리 중인 HandlerMethod에서 @Auth 어노테이션을 가져오는 부분입니다. 
//		즉, 해당 요청을 처리할 메서드에 @Auth 어노테이션이 붙어 있는지 확인하는 것입니다.
//		예를 들면, 컨트롤러의 개별 메서드 위에 @Auth를 붙였을 경우 해당 메서드에 적용된 @Auth 정보를 가져오게 됩니다.
		// 과제. HandlerMethod의 @Auth가 없는 경우 Type(Class)의 @Auth 가져오기 메서드가 있음
		if (auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
//			handlerMethod.getBeanType()는 현재 처리 중인 메서드가 속한 클래스의 정보를 가져옵니다.
//			getAnnotation(Auth.class)는 해당 클래스에 @Auth 어노테이션이 붙어 있는지 확인하고, 있으면 해당 어노테이션 정보를 반환합니다.
//			예를 들면, 전체 컨트롤러 클래스 위에 @Auth를 붙였을 경우 해당 클래스에 적용된 @Auth 정보를 가져오게 됩니다.
		}

		// 4. @Auth 가 없는 경우
		if (auth == null) {
			return true;
		}

		// 5. @Auth가 붙어 있는 경우, 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		// 6. 권한(Authorization) 체크를 위해서 @Auth의 Role 가져오기("USER", "ADMIN")
		String role = auth.Role();
		if ("USER".equals(role)) {
			return true;
		}
		String authUserRole = authUser.getRole();

		if (!"ADMIN".equals(authUserRole)) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
//		 해당 페이지가 ADMIN 권한이고, 세션에 저장되어 있는 회원이 USER인 경우
		if (role.equals("ADMIN") && authUserRole.equals("USER")) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		// 6. 인증 확인
		return true;
	}

}
