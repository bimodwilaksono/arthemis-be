package com.enigma.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminOnlyEndpointsConfig {
    private final Map<String, List<String>> ADMIN_ONLY_URIS;

    public AdminOnlyEndpointsConfig() {
        this.ADMIN_ONLY_URIS = new HashMap<>();
        ADMIN_ONLY_URIS.put("/api/v1/campsite", Arrays.asList( "PUT", "DELETE"));
        ADMIN_ONLY_URIS.put("/api/v1/order", Arrays.asList("PUT", "DELETE"));
//        ADMIN_ONLY_URIS.put("/api/v1/payment", Arrays.asList("PUT", "DELETE"));
    }

    public Map<String, List<String>> getAdminOnlyUris() {
        return ADMIN_ONLY_URIS;
    }
}
