package mk.ukim.finki.dick.prezemiakcijabackend.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.config.JwtAuthConstants;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.JWTResponse;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.LoginDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RegistrationDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.UserDetailsDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.*;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.UserRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public List<User> findAll() {
//        return this.userRepository.findAll();
//    }
//
//    @Override
//    public List<User> findAllByRole(String roleName) throws InvalidRoleName {
//        Role role = this.validateRoleName(roleName);
//
//        return this.userRepository.findAllByRole(role);
//    }

    @Override
    public User findByEmail(String userEmail) {
        return this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound(userEmail));
    }

    @Override
    public JWTResponse registerUser(RegistrationDto registrationDto) throws ConstraintViolationException,
            PasswordsDoNotMatch, UserAlreadyExists, JsonProcessingException {
        this.checkRegistrationDtoForViolations(registrationDto);
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        User newUser = this.userRepository.save(new User(registrationDto));

        return this.generateToken(newUser);
    }

    @Override
    public JWTResponse loginUser(LoginDto loginDto) throws ConstraintViolationException, JsonProcessingException,
            UserNotFound {
        this.checkLoginDtoForViolations(loginDto);
        String userEmail = loginDto.getEmail();
        String userPassword = loginDto.getPassword();
        User existingUser = this.findByEmail(userEmail);

        if (!passwordEncoder.matches(userPassword, existingUser.getPassword())) {
            throw new InvalidCredentials();
        }

        return this.generateToken(existingUser);
    }

//    @Override
//    public boolean deleteUser(String userEmail) throws UserNotFound {
//        User existingUser = this.findByEmail(userEmail);
//
//        this.userRepository.delete(existingUser);
//
//        try {
//            existingUser = this.findByEmail(userEmail);
//
//            return false;
//        } catch (UserNotFound e) {
//            return true;
//        }
//    }
//
//    private Role validateRoleName(String roleName) {
//        boolean validRoleName = false;
//
//        for (Role role : Role.values()) {
//            if (role.toString().equals(roleName)) {
//                validRoleName = true;
//                break;
//            }
//        }
//
//        if (validRoleName) {
//            return Role.valueOf(roleName);
//        } else {
//            throw new InvalidRoleName(roleName);
//        }
//    }

    private void checkRegistrationDtoForViolations(RegistrationDto registrationDto) {
        Set<ConstraintViolation<RegistrationDto>> constraintViolations = this.validator.validate(registrationDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Registration' object is not valid.", constraintViolations);
        }

        String password = registrationDto.getPassword();
        String confirmPassword = registrationDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            throw new PasswordsDoNotMatch();
        }

        try {
            User existingUser = this.findByEmail(registrationDto.getEmail());

            throw new UserAlreadyExists(existingUser);
        } catch (UserNotFound ignored) {
        }
    }

    private void checkLoginDtoForViolations(LoginDto loginDto) {
        Set<ConstraintViolation<LoginDto>> constraintViolations = this.validator.validate(loginDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Login' object is not valid.", constraintViolations);
        }
    }

    private JWTResponse generateToken(User user) throws JsonProcessingException {
        return new JWTResponse(user.getEmail(),
                JWT.create()
                        .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(user)))
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                        .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes())));
    }
}
