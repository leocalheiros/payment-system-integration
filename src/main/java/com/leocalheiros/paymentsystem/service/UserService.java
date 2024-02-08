package com.leocalheiros.paymentsystem.service;

import com.leocalheiros.paymentsystem.dto.UserResponse;
import com.leocalheiros.paymentsystem.entity.User;
import com.leocalheiros.paymentsystem.exceptions.ExistentEmailException;
import com.leocalheiros.paymentsystem.repository.UserRepository;
import com.leocalheiros.paymentsystem.util.RandomString;
import jakarta.mail.MessagingException;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new ExistentEmailException("This email already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.generateRandomString(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPassword());

        mailService.sendVerificationEmail(user);
        return userResponse;
    }

    public JSONObject verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);

        if(user == null || user.isEnabled()){
            JSONObject response = new JSONObject();
            response.put("message", "verify_fail");
            return response;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            user.setRole("user");
            userRepository.save(user);
            JSONObject response = new JSONObject();
            response.put("message", "verify_success");
            return response;
        }
    }
}
