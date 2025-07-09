package de.muenchen.gitwrapped.api.github;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GitHubUserClient {

    private static final Logger LOG = LoggerFactory.getLogger(GitHubUserClient.class);
    private static final String GITHUB_USERS_URL = "https://api.github.com/users/";

    /**
     * Fetches user info from GitHub.
     *
     * @param username GitHub username
     * @param accessToken OAuth access token
     * @return JSON response as String or null if failed
     */

    public String fetchUserInfo(final String username, final String accessToken) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        final String url = GITHUB_USERS_URL + username;

        try {
            final ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (Exception e) {
            LOG.error("Fehler beim Aufruf der GitHub API für Benutzer '{}': {}", username, e.getMessage());
            return null;
        }
    }
}
