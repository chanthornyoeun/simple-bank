package com.example.bank.services;

import com.example.bank.dto.UserDTO;
import com.example.bank.models.Role;
import com.example.bank.models.User;
import com.example.bank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public long count() {
        return this.userRepository.count();
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        List<Role> roles = userDTO.getRoles().stream().map(roleService::getRoleById).toList();
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // TODO
    public User getCurrentUser() {
        return null;
    }

}
