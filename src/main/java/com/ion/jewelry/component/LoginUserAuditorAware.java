package com.ion.jewelry.component;


import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            return Optional.of(username);
        }
        return Optional.empty();
    }
}

