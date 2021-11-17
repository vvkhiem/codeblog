package com.vuvankhiem.blogzine.Common;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static CustomUser getPrincipal() {
        return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
