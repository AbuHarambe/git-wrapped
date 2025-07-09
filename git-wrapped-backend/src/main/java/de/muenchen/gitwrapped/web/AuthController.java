package de.muenchen.gitwrapped.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller für Authentifizierungs-Endpunkte.
 */
@RestController
public class AuthController {

    /**
     * Liefert den angemeldeten Benutzernamen zurück, falls authentifiziert.
     * Wird automatisch auf 401 gesetzt, wenn keine Authentifizierung vorliegt.
     */
    @GetMapping("/api/me")
    public ResponseEntity<String> getAuthenticatedUser(OAuth2AuthenticationToken token) {
        return ResponseEntity.ok(token.getPrincipal().getName());
    }
}
