package com.example.bank.config;

import com.example.bank.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private final UserService userService;

    SpringSecurityAuditorAware(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(userService.getCurrentUser().getUsername());
    }

}
