package ru.neoflex.user_storage.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class UserDto {
    private Integer id;

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;
}