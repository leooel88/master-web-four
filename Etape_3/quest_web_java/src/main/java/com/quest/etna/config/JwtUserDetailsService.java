package com.quest.etna.config;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class JwtUserDetailsService implements UserDetailsService{

    @Autowired
	UserRepository userRepository;
    
    @Override
	@Transactional
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByUsernameIgnoreCase(username);
        if (user != null) {
            return new JwtUserDetails(user);
        } else {
            return null;
        }
	}
    
}
