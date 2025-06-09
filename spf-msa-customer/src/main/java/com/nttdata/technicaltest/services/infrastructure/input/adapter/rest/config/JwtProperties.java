package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class JwtProperties {
    @NotBlank
    @Size(min = 32, max = 64)
    private String secret;
    @NotNull
    @Min(1L)
    private Long expiration;
}
