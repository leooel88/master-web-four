package com.quest.etna.config;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

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
            System.out.println("TROUVEEEEE : " + userDetails.getId());
            return userDetails;
        } else {
            return null;
        }
	}
    
}
