package com.qeema.practicaltest.ordermanagement.infrastructure.secutiry;

import java.io.Serializable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.access.PermissionEvaluator;

public class CustomRolesEvaluator implements PermissionEvaluator {

    private final boolean securityEnabled;

    public CustomRolesEvaluator(boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || !(permission instanceof String)){
            return false;
        }
        return hasPrivilege(auth, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, permission.toString().toUpperCase());
    }
    private boolean hasPrivilege(Authentication auth, String permission) {
        if(!securityEnabled) return true;
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}