package com.example.timetracking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.timetracking.model.User;

@Service
public class Mailer {
	
	private Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	/**
	 * @Value("spring.mail.username")
	 */
	private String sender;
	
	/**
	 * @Value("spring.mail.password")
	 */
	private String password;
	
	private JavaMailSender javaMailer;

	@Autowired
	public Mailer(JavaMailSender javaMailer) {
		this.javaMailer = javaMailer;
	}

	@Async
	public void sendWelcomeEmail(User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(user.getEmail());
		email.setFrom("Foobar");
		email.setSubject("Spring Boot is awesome!");
		email.setText("Why aren't you using Spring Boot?");

		logger.info(sender);
		logger.info(password);
		
		javaMailer.send(email);
		
		System.out.println("Email Sent!");
	}

}
