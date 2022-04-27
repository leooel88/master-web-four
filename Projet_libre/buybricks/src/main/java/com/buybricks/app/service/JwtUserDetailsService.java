package com.buybricks.app.service;

import com.buybricks.app.model.JwtUserDetails;
import com.buybricks.app.model.User;
import com.buybricks.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService{

    @Autowired
	UserRepository userRepository;
    
    @Override
	@Transactional
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByUsernameIgnoreCase(username);
        if (user != null) {
            JwtUserDetails userDetails = new JwtUserDetails(user);
            return userDetails;
        } else {
            return null;
        }
	}
    
}
