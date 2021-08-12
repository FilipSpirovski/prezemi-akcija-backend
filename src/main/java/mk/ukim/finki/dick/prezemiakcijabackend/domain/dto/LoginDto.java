package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginDto {

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;
}
