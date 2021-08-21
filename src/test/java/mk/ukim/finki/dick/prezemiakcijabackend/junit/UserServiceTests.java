package mk.ukim.finki.dick.prezemiakcijabackend.junit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mk.ukim.finki.dick.prezemiakcijabackend.config.JwtAuthConstants;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.JWTResponse;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.LoginDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RegistrationDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.UserDetailsDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.UserNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.UserRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.UserService;
import mk.ukim.finki.dick.prezemiakcijabackend.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    private UserService userService;

    @Before
    public void init() {
        this.userService = Mockito.spy(new UserServiceImpl(this.userRepository, this.validator, this.passwordEncoder));
    }

    @Test
    public void findByEmailWithValidEmail() {
        String email = "user@students.finki.ukim.mk";
        RegistrationDto registrationDto = createRegistrationDto();
        User user = new User(registrationDto);

        Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Assert.assertEquals(user, this.userService.findByEmail(email));
        Mockito.verify(this.userService).findByEmail(email);
    }

    @SneakyThrows
    @Test
    public void registerUserWithValidData() {
        RegistrationDto registrationDto = createRegistrationDto();
        User user = new User(registrationDto);
        Set<ConstraintViolation<RegistrationDto>> constraintViolations = this.validator.validate(registrationDto);

        Mockito.when(this.validator.validate(registrationDto)).thenReturn(constraintViolations);
        Mockito.when(this.userRepository.findByEmail(user.getEmail())).thenThrow(new UserNotFound(user.getEmail()));
        Mockito.when(this.passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        Assert.assertEquals(generateJwtResponse(user), this.userService.registerUser(registrationDto));
        Mockito.verify(this.userService).registerUser(registrationDto);
    }

    @SneakyThrows
    @Test
    public void loginUserSuccessful() {
        String email = "user@students.finki.ukim.mk";
        String password = "password";
        LoginDto loginDto = new LoginDto(email, password);
        User user = new User(createRegistrationDto());
        Set<ConstraintViolation<LoginDto>> constraintViolations = this.validator.validate(loginDto);

        Mockito.when(this.validator.validate(loginDto)).thenReturn(constraintViolations);
        Mockito.when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncoder.matches(password, password)).thenReturn(true);

        Assert.assertEquals(generateJwtResponse(user), this.userService.loginUser(loginDto));
        Mockito.verify(this.userService).loginUser(loginDto);
    }

    private JWTResponse generateJwtResponse(User user) throws JsonProcessingException {
        return new JWTResponse(user.getEmail(),
                JWT.create()
                        .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(user)))
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                        .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes())));
    }

    private RegistrationDto createRegistrationDto() {
        String email = "user@students.finki.ukim.mk";
        String password = "password";
        String confirmPassword = "password";
        String role = Role.ROLE_USER.toString();
        String name = "John";
        String surname = "Doe";

        return new RegistrationDto(email, password, confirmPassword, role, name, surname);
    }
}
