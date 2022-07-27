package com.fullstackproject.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MySQLUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User findUser = userRepository.findByUsername(username);
		if(findUser == null) {
			throw new UsernameNotFoundException(username);
		} 
		return new org.springframework.security.core.userdetails.User(findUser.getUsername(), findUser.getPassword(), getAuthorities());
	}
	public UserDetails save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), getAuthorities());
	}
	private List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authList;
	}

}
