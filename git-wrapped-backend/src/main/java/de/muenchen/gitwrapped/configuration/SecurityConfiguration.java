package de.muenchen.gitwrapped.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * The central class for configuration of all security aspects.
 * Automatically used when not running with profile `no-security`.
 * Configures all endpoints to require authentication via access token.
 * (except the Spring Boot Actuator endpoints)
 * Additionally it configures the use of the {@link UserInfoAuthoritiesService}.
 */
@RequiredArgsConstructor
@Configuration
@Profile("!no-security")
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Import(RestTemplateAutoConfiguration.class)
public class SecurityConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;

    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher("/actuator/info"),
                                AntPathRequestMatcher.antMatcher("/actuator/health"),
                                AntPathRequestMatcher.antMatcher("/actuator/health/liveness"),
                                AntPathRequestMatcher.antMatcher("/actuator/health/readiness"),
                                AntPathRequestMatcher.antMatcher("/actuator/metrics"))
                        .permitAll()
                        .requestMatchers("/login**", "/oauth2/**").permitAll() // <-- neu: Login & Redirects
                        .anyRequest().authenticated())
                .oauth2Login(); // <-- neu: GitHub Login aktivieren

        return http.build();
    }
}
