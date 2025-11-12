package com.events.eventsbooking.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Email
    @NotBlank(message = "Email must not be Blank")
    @NotNull(message = "Email must not be null")
    private String email;

    @NotBlank(message = "Password must not be Blank")
    @NotNull(message = "Password must not be null")

    @Size(min = 7,message = ("Password must be at least 7 characters minimum"))
    private String password;

}
