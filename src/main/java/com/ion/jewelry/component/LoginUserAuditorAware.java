package com.ion.jewelry.component;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.ion.jewelry.repository.MemberRepository;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {
	@Autowired
	MemberRepository memberRepository;
    @Override
    public Optional<String> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            return Optional.of(username);
        }
        return Optional.empty();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (null == authentication || !authentication.isAuthenticated()) {
//            return null;
//        }
//        Member user = (Member) authentication.getPrincipal();
//        return Optional.of(user.getAccount());
    }
}

