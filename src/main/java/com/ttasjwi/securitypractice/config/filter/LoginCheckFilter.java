package com.ttasjwi.securitypractice.config.filter;

import com.ttasjwi.securitypractice.config.session.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/api/auth/login", "/api/auth/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                validateSession(httpRequest);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외를 잡아서 로깅할 수 있지만, 톰캣까지 예외를 보내주어야 한다.
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    private static void validateSession(HttpServletRequest httpRequest) {
        // 세션을 찾아 사용하는 시점에는, create:false를 사용하여 새로 생성하지 않는다.
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            throw new AuthenticationException("인증에 실패했습니다. 로그인 해주세요.");
        }
    }

    /**
     * 로그인 대상인지 여부를 반환함.
     * @param requestURI : 요청 URI
     * @return 로그인 대상인지 여부(true를 반환하면 인증이 필요하고, false이면 화이트리스트이므 인증이 필요하지 않음)
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
