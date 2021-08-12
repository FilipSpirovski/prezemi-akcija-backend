package mk.ukim.finki.dick.prezemiakcijabackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentDto {

    @NotNull
    private Long forumId;

    @NotNull
    @NotEmpty
    private String text;
}
