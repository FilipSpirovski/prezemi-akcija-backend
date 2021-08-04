package mk.ukim.finki.dick.prezemiakcijabackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.RegistrationDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class User implements Serializable {

    @Id
    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String name;

    private String surname;

    public User(RegistrationDto registrationDto) {
        this.email = registrationDto.getEmail();
        this.password = registrationDto.getPassword();
        this.role = Role.valueOf(registrationDto.getRole());
        this.name = registrationDto.getName();
        this.surname = registrationDto.getSurname();
    }
}
