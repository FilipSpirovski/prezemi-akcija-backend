package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.Data;

@Data
public class JWTResponse {

    private String email;
    private String jwt;

    public JWTResponse(String email, String jwt) {
        this.email = email;
        this.jwt = jwt;
    }
}
