package com.example.algamoneyapi.token;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse)response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshToeknNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBory(token);
		
		
		return body;
	}

	private void removerRefreshTokenDoBory(DefaultOAuth2AccessToken token) {
		// TODO remover o token
		token.setRefreshToken(null);
		
		
	}

	private void adicionarRefreshToeknNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie c = new Cookie("refreshToken", refreshToken);
		c.setHttpOnly(true);
		c.setSecure(false); // T0D0: Mudar para true em produção
		c.setPath(req.getContextPath() + "/oauth/token");
		c.setMaxAge(2592000);
		resp.addCookie(c);
		
	}



}
