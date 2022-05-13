package by.rom.projectapi.service;

import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.ERole;
import by.rom.projectapi.model.User;
import by.rom.projectapi.repository.RoleRepository;
import by.rom.projectapi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user, ERole role){
        user.setRole(roleRepository.findByName(Objects.requireNonNullElse(role, ERole.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("user - " + user);
        return userRepository.save(user);
    }

    public User findByUserName(String userName){
        return userRepository
                .findByUserName(userName)
                .orElseThrow(()-> new NotFoundException("User didn't found"));
    }

    public User findByUserNameAndPassword(String userName, String password){
        return userRepository.findByUserName(userName)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }
}
