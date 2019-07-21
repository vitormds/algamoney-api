package com.example.algamoneyapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	private String originPermitida = "http://localhost:8000"; //TODO configurar para diferentes ambientes
	private String verbos = "POST, GET, DELETE, PUT, OPTIONS";
	private String allowHeaders = "Authorization, Content-Type, Accept";
	private String tempoHoras = "3600"; 

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		
		res.setHeader("Access-Control-Allow-Origin", originPermitida);
		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(req.getMethod()) && originPermitida.equals(req.getHeader("Origin"))){
			res.setHeader("Access-Control-Allow-Methods", verbos);
			res.setHeader("Access-Control-Allow-Headers", allowHeaders);
			res.setHeader("Access-Control-Max-Age", tempoHoras);

 			res.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
