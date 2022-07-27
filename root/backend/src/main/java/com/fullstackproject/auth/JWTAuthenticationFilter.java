package com.fullstackproject.auth;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fullstackproject.auth.AuthConstants.*;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			com.fullstackproject.auth.User cred = new ObjectMapper().readValue(req.getInputStream(), com.fullstackproject.auth.User.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken (
							cred.getUsername(),
							cred.getPassword(),
							new ArrayList<>()
							)
					);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain, Authentication auth) throws ServletException, IOException {
		String token = JWT.create()
						  .withSubject(( (org.springframework.security.core.userdetails.User)auth.getPrincipal() ).getUsername())
						  .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						  .sign(HMAC512(SECRET.getBytes()));
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException  {
		super.unsuccessfulAuthentication(req, res, failed);
	}
}
