package com.formapp.damnjan.utils;

import com.formapp.damnjan.entities.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.formapp.damnjan.utils.PrincipalHelper.getPrincipal;

public class CustomAuditAware implements AuditorAware<UserEntity> {
    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Object principal = getPrincipal();

        if (principal == null) {
            return Optional.empty();
        }

        return Optional.of((UserEntity) principal);
    }
}
