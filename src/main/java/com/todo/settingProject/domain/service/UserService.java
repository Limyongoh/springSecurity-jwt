package com.todo.settingProject.domain.service;

import com.todo.settingProject.domain.dto.user.UserDto;
import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup (UserDto userDto) throws  Exception{
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일 입니다.");
        }
        if(userRepository.findByNickName(userDto.getNickName()).isPresent()){
            throw new Exception("이미 존재하는 닉네임 입니다.");
        }
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .nickName(userDto.getNickName())
                .name(userDto.getName())
                .build();

        user.setPasswordEncoder(passwordEncoder);
        userRepository.save(user);
    }
}
