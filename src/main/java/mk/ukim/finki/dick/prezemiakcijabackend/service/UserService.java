package mk.ukim.finki.dick.prezemiakcijabackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.JWTResponse;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.LoginDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RegistrationDto;

import java.util.List;

public interface UserService {

//    List<User> findAll();
//
//    List<User> findAllByRole(String roleName);

    User findByEmail(String userEmail);

    JWTResponse registerUser(RegistrationDto registrationDto) throws JsonProcessingException;

    JWTResponse loginUser(LoginDto loginDto) throws JsonProcessingException;

//    boolean deleteUser(String userEmail);
}
