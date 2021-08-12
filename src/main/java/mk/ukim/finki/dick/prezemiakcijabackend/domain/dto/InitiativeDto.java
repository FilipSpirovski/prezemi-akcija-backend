package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class InitiativeDto {

    @NotNull
    @NotEmpty
    private String categoryName;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String scheduledFor;

    @NotNull
    @NotEmpty
    private String eventTypeName;

    @NotNull
    @NotEmpty
    private String location;
}
