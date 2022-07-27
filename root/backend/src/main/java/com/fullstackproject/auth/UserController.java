package com.fullstackproject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private MySQLUserDetailsService mySQLUserDetailsService;
	@PostMapping("/register")
	public void register(@RequestBody User user) {
	mySQLUserDetailsService.save(user);
	}
}
