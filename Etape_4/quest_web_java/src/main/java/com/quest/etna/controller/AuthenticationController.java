package com.quest.etna.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.JwtUserDetailsService;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.quest.etna.repositories")
public class AuthenticationController {

    private static UserRepository userRepositoryAuth;

    @Autowired
    private UserRepository userRepo;

    @PostConstruct
    private void init() {
        userRepositoryAuth = this.userRepo;
    }

    @Autowired
	JwtTokenUtil jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
	private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
	private AuthenticationManager authenticationManager;
    
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");
        
        if (username == null || username == "" || password == null || password == "") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "Username or password is missing !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        else if (userRepositoryAuth.findFirstByUsernameIgnoreCase(username) != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This username has already been used !");
            return new ResponseEntity<Object>(map, HttpStatus.CONFLICT);
        }
        try {
            User savingResponse = userRepositoryAuth.save(new User(username, passwordEncoder.encode(password)));
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "User couldn't be created");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }
            UserDetails userDetails = new UserDetails(savingResponse);
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", userDetails.getUsername());
            map.put("role", userDetails.getRole().toString());
            return new ResponseEntity<Object>(map, HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<?> readUserDetails(@RequestBody Map<String, String> body) throws Exception {
        JwtUserDetails userDetails = null;
        try {
            userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        
        if (userDetails == null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "User not logged in !");
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }
        String username = userDetails.getUsername();
        User user = userRepo.findFirstByUsernameIgnoreCase(username);
        if (user != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Userdetails", userDetails.toString());
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "User not logged in !");
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> body) throws Exception {
        String username = body.get("username");
        String password = body.get("password");

        HashMap<String,String> authentication = authenticate(username, password);
        if (authentication == null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "Couldn't log in !");
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }
        final JwtUserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(username);

        final String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    public HashMap<String, String> authenticate(String username, String password) {
        boolean authenticationWorked = true;
        HashMap<String, String> res = new HashMap<String, String>();
        try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
            System.out.println(e.toString());
            authenticationWorked = false;
            res = null;
        }

        if (authenticationWorked) {
            final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            final String jwtToken = jwtUtils.generateToken(userDetails);
    
            res.put("token", jwtToken);
        }
        return res;
    }
}
