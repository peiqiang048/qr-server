package com.cnc.qr.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.model.CommonOutputResource;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a
 * valid user is found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        boolean isToken = this.tokenProvider.validateToken(jwt, httpServletRequest.getRequestURI());
        if (StringUtils.hasText(jwt) && isToken) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // access_tokenチェック失敗
        if ((httpServletRequest.getRequestURI().contains("/api/csmb") && !httpServletRequest
            .getRequestURI().contains("/getToken") && !httpServletRequest.getRequestURI()
            .contains("/weChatAliPayBackUrl") && !httpServletRequest.getRequestURI()
            .contains("/sbPaymentCallBack") && !httpServletRequest.getRequestURI()
            .contains("/getPrepaymentPrintData")) || (
            httpServletRequest.getRequestURI().contains("/api/stpd") && !httpServletRequest
                .getRequestURI().contains("/getPrintOrderList") && !httpServletRequest
                .getRequestURI().contains("/changePrintStatus") && !httpServletRequest
                .getRequestURI().contains("/login") && !httpServletRequest
                .getRequestURI().contains("/getUserAndLanguageList") && !httpServletRequest
                .getRequestURI().contains("/storeIdVerification")) || (
            httpServletRequest.getRequestURI().contains("/api/pc") && !httpServletRequest
                .getRequestURI().contains("/login")) || (
            httpServletRequest.getRequestURI().contains("/api/stmb") && !httpServletRequest
                .getRequestURI().contains("/login") && !httpServletRequest
                .getRequestURI().contains("/getUserList"))) {
            if (jwt == null || !isToken) {
                response401(servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void response401(ServletResponse resp) throws IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            CommonOutputResource resource = new CommonOutputResource();
            resource.setResultCode(ResultCode.UNAUTHORIZED.getResultCode());
            resource.setMessage(ResultCode.UNAUTHORIZED.getMessage());
            out.append(JSONObject.toJSONString(resource));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
