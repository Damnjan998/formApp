package com.formapp.damnjan.utils;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalHelper {

    public static String getAuthUserRole() {
        String role = null;
        Object principal = getPrincipal();
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

    public static Integer getLoggedInUserId() {
        Integer id = null;
        Object principal = getPrincipal();
        if (principal instanceof UserEntity userEntity) {
            id = userEntity.getId();
        }

        return id;
    }

    public static Object getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }
}
