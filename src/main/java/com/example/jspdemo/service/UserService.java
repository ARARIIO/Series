package com.example.jspdemo.service;

import com.example.jspdemo.JWT.JwtUtil;
import com.example.jspdemo.model.User;
import com.example.jspdemo.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user) {
        logger.debug("Registering user with email: {}", user.getEmail());
        try {
            String token = jwtUtil.generateToken(user.getEmail());
            user.setToken(token);
            user.setEnabled(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            sendConfirmationEmail(user);
            logger.info("User registered successfully with email: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Error registering user with email: {}", user.getEmail(), e);
        }
    }
    @Transactional
    public boolean confirmUser(String token) {
        String email = jwtUtil.extractEmail(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(true);
            user.setToken(null); // Очистите токен после активации
            userRepository.save(user);
            return true;
        } else {
            logger.error("No user found with token {}", token);
            return false;
        }
    }

    private void sendConfirmationEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8091/confirm-account?token=" + user.getToken());
        mailSender.send(mailMessage);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.jspdemo.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}
