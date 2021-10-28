package com.ion.jewelry.component;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {
	
	public Optional<String> getCurrentAuditor(){
		return Optional.of("AdminServer");
	}
	
}
