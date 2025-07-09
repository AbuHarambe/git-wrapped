package de.muenchen.gitwrapped.web;

import de.muenchen.gitwrapped.api.github.GitHubUserClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GitHubTestController {

    private static final Logger LOG = LoggerFactory.getLogger(GitHubTestController.class);

    private final GitHubUserClient gitHubUserClient;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/api/github/{username}")
    public ResponseEntity<String> fetchGitHubUser(
            @PathVariable String username,
            OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        if (client == null || client.getAccessToken() == null) {
            LOG.warn("Kein gültiger GitHub-OAuth2-Client vorhanden.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Nicht authentifiziert bei GitHub");
        }

        String accessToken = client.getAccessToken().getTokenValue();

        String userInfo = gitHubUserClient.fetchUserInfo(username, accessToken);
        if (userInfo == null || userInfo.isBlank()) {
            LOG.warn("Keine Daten für Benutzer {} von GitHub erhalten.", username);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("GitHub API-Aufruf fehlgeschlagen oder leer");
        }

        return ResponseEntity.ok(userInfo);
    }
}
