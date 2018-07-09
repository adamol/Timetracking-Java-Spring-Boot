package com.example.timetracking.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.timetracking.model.Token;
import com.example.timetracking.model.User;
import com.example.timetracking.model.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final String JWT_SECRET = "somerandomstring";

	@Autowired
	private UsersRepository usersRepository;
	
	@PostMapping("/register")
	@ResponseBody
	public Token register(@RequestBody User user) {
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		
		user.setPassword(hashedPassword);
		
		usersRepository.save(user);
		
        Token token = generateTokenForUser(user);
		
		return token;
	}
	
	@PostMapping("/get-token")
	@ResponseBody
	public Token getToken(@RequestBody User user) {
		Optional<User> retrievedUser = usersRepository.findByEmail(user.getEmail());
		
		if (! BCrypt.checkpw(user.getPassword(), retrievedUser.get().getPassword())) {
			return new Token();
		}		
		
		Token token = generateTokenForUser(user);

		return token;
	}
	
	@PostMapping("/parse-token")
	@ResponseBody
	public User parseToken(@RequestBody Token token) {
	    User user = new User();
	    
		try {
		    Claims claims = Jwts.parser()
		    	.setSigningKey(JWT_SECRET)
		    	.parseClaimsJws(token.getToken())
		    	.getBody();
		    
		    user.setEmail((String) claims.get("email"));
		    user.setPassword((String) claims.get("password"));
		} catch (SignatureException e) {
		}
		
		return user;
	}

	private Token generateTokenForUser(User user) {
		Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());
		
        String tokenString = Jwts.builder()
	            .setClaims(claims)
	            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
	            .compact();
        
        Token token = new Token();

        token.setToken(tokenString);
        
        return token;
	}
}
