package by.rom.projectapi.controller;

import by.rom.projectapi.config.jwt.JwtProvider;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.ERole;
import by.rom.projectapi.model.Role;
import by.rom.projectapi.model.User;
import by.rom.projectapi.model.dto.AuthenticationRequestDto;
import by.rom.projectapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRequestDto> registerUser(@RequestBody @Valid AuthenticationRequestDto authRequest){
        User user = User.builder()
                .userName(authRequest.getLogin())
                .password(authRequest.getPassword())
                .build();
        if (authRequest.getRole() != null){
            user.setRole(Role.builder().name(authRequest.getRole()).build());
        }
        else {
            user.setRole(Role.builder().name(ERole.ROLE_USER).build());
        }
        userService.saveUser(user);
        return new ResponseEntity<>(authRequest, HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthenticationRequestDto authRequest){
        User user = userService.findByUserNameAndPassword(authRequest.getLogin(), authRequest.getPassword());
        if (user == null){
            throw new NotFoundException("User: not found");
        }
        String token = jwtProvider.generateToken(user.getUserName());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
