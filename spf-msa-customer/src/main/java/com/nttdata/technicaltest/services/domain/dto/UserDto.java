package com.nttdata.technicaltest.services.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank
    @Size(min = 4, max = 25)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9.\\-_]+[a-zA-Z0-9]+$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$")
    private String password;
}
