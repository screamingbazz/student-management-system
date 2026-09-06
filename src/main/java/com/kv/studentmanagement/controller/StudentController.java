package com.kv.studentmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    /**
     * Accessible by ANY authenticated user (Students, Admins, etc.)
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the Student Dashboard!");
        response.put("username", userDetails.getUsername());
        response.put("authorities", userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }

    /**
     * Accessible ONLY by users with ROLE_ADMIN
     * If a student tries to hit this with a valid token, they get a 403 Forbidden!
     */
    @GetMapping("/admin-settings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getAdminSettings() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Success");
        response.put("data", "Confidential Admin Panel Data: System status is nominal.");

        return ResponseEntity.ok(response);
    }
}
