package com.todo.settingProject.domain.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private String email;

    private String password;

    private String nickName;

    private String name;
}
