package com.example.algamoneyapi.resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoneyapi.config.property.AlgamoneyApiProperty;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired 
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie c = new Cookie("refreshToken", null);
		c.setHttpOnly(true);
		c.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps()); // T0D0: Em producao sera true;
		c.setPath(req.getContextPath()+"/oauth/token");
		c.setMaxAge(0);
		resp.addCookie(c);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
		
	}
	
}
