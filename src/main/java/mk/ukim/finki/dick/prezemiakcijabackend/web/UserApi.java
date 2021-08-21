package mk.ukim.finki.dick.prezemiakcijabackend.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.JWTResponse;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.LoginDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RegistrationDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.InvalidCredentials;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.PasswordsDoNotMatch;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.UserAlreadyExists;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.UserNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class UserApi {

    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = this.userService.findAll();
//
//        return ResponseEntity.ok(users);
//    }

//    @GetMapping("/with-role/{role}")
//    public ResponseEntity<List<User>> getUsersWithRole(@PathVariable String role) {
//        List<User> users = this.userService.findAllByRole(role);
//
//        return ResponseEntity.ok(users);
//    }

//    @GetMapping("/single-user")
//    public ResponseEntity getUserDetails(@RequestBody String userEmail) {
//        try {
//            User user = this.userService.findByEmail(userEmail);
//
//            return ResponseEntity.ok(user);
//        } catch (UserNotFound e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody RegistrationDto registrationDto) {
        try {
            JWTResponse jwtResponse = this.userService.registerUser(registrationDto);

            return ResponseEntity.ok(jwtResponse);
        } catch (ConstraintViolationException | PasswordsDoNotMatch | UserAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity signInExistingUser(@RequestBody LoginDto loginDto) {
        try {
            JWTResponse jwtResponse = this.userService.loginUser(loginDto);

            return ResponseEntity.ok(jwtResponse);
        } catch (ConstraintViolationException | UserNotFound | InvalidCredentials e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity deleteExistingUser(@RequestBody String userEmail) {
//        try {
//            boolean result = this.userService.deleteUser(userEmail);
//
//            if (result) {
//                String message = String.format("The user with the provided email (%s) was successfully deleted.", userEmail);
//
//                return ResponseEntity.ok(message);
//            } else {
//                return ResponseEntity.internalServerError().build();
//            }
//        } catch (UserNotFound e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
}
