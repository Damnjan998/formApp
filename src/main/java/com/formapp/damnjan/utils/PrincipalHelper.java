package com.formapp.damnjan.utils;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalHelper {

    public static String getAuthUserRole() {
        String role = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userEntity) {
            role = userEntity.getRole();
        }

        return role;
    }

    public static void checkPermission() {
        String authUserRole = getAuthUserRole();
        if (authUserRole.equals("USER")) {
            throw ExceptionSupplier.userDoesNotHavePermission.get();
        }
    }
}
