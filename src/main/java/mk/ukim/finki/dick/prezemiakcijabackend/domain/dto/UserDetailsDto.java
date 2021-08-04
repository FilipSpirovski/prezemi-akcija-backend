package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.User;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    private String id;

    private Role role;

    public static UserDetailsDto of(User user) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();

        userDetailsDto.setId(user.getEmail());
        userDetailsDto.setRole(user.getRole());

        return userDetailsDto;
    }

    public static UserDetailsDto empty() {
        return new UserDetailsDto(null, null);
    }
}
