package com.example.demo.dto;


import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull(message = "Email Field Should Not be empty")
    @Email(message = "Invalid email id")
    @Size(min = 10,max = 20,message = "email should be in the range of 10-20 characters")
    private String email;
    private String password;
}
