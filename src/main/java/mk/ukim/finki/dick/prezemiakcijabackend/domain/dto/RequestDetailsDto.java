package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.Data;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;

@Data
public class RequestDetailsDto {

    private String id;

    private Role role;
}
