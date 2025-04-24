package org.seba.api.controllers;

import lombok.RequiredArgsConstructor;
import org.seba.bll.services.dashboard.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getDashboard(@AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.ok(dashboardService.getDashboardForUser(currentUser.getUsername()));
    }
}