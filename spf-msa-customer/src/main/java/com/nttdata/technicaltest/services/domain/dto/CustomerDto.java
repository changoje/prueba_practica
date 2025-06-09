package com.nttdata.technicaltest.services.domain.dto;

import com.nttdata.technicaltest.services.domain.enums.GenderTypeEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one numeric digit")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain at least one letter")
    private String password;

    @NotNull(message = "Address cannot be null")
    @Size(min = 8, max = 20, message = "Address must be between 8 and 20 characters")
    private String address;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits (no letters or special characters allowed)")
    private String phone;

    @NotNull(message = "Gender ENUM must be selected (MALE/FEMALE)")
    private GenderTypeEnum genderTypeEnum;

    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 130, message = "Age must be less than or equal to 130")
    private int age;
}
