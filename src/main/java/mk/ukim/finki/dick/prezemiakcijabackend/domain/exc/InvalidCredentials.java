package mk.ukim.finki.dick.prezemiakcijabackend.domain.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials() {
        super("The provided 'Email' and 'Password' fields are invalid.");
    }
}
