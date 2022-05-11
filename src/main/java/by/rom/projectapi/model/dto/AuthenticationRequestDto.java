package by.rom.projectapi.model.dto;

import by.rom.projectapi.model.ERole;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {

    @NotBlank
    private String login;
    @NotBlank
    private String password;

    private ERole role;
}
