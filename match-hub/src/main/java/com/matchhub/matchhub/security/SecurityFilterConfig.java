package com.matchhub.matchhub.security;

import com.matchhub.matchhub.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.matchhub.matchhub.domain.enums.Permission.*;
import static com.matchhub.matchhub.domain.enums.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private static final String[] WHITE_LIST_URL = {
            // Everyone has the right to try to authenticate themselves
            "/auth/**",
            // Everyone can read screen comments
            "/screens/*",
            "/screens/*/*",
            // Use h2
            "/h2-console/**",
            // Use Swagger 2 and 3
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            // Use Swagger UI
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/configuration/ui",
            // Use Swagger UI Security
            "/configuration/security",
            // Swagger UI Dependency
            "/webjars/**"
            };

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll()
                            /* ROLE: HUB USERS */
                                /* HUB USERS */
                                // Roles
                            .requestMatchers("/hubusers/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name(), HUBUSER.name())
                                // Authority
                            .requestMatchers(GET, "/hubusers/**").
                                hasAnyAuthority(HUBUSER_READ.name())
                            .requestMatchers(PUT, "/hubusers/**").
                                hasAnyAuthority(HUBUSER_UPDATE.name())
                            .requestMatchers(PATCH, "/hubusers/**").
                                hasAnyAuthority(HUBUSER_PATCH.name())
                                /* SCREENS */
                                // Roles
                            .requestMatchers("/screens/*/comments/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name(), HUBUSER.name())
                                // Authority
                            .requestMatchers(GET, "/screens/*/comments/**").
                                hasAnyAuthority(HUBUSER_READ.name())
                            .requestMatchers(PUT, "/screens/*/comments/**").
                                hasAnyAuthority(HUBUSER_UPDATE.name())
                            .requestMatchers(POST, "/screens/*/comments/**").
                                hasAnyAuthority(HUBUSER_CREATE.name())
                            .requestMatchers(DELETE, "/screens/*/comments/**").
                                hasAnyAuthority(HUBUSER_DELETE.name())
                                /* EVALUATIONS */
                                // Roles
                            .requestMatchers("/comments/*/evaluations/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name(), HUBUSER.name())
                                // Authority
                            .requestMatchers(PUT, "/comments/*/evaluations/**").
                                hasAnyAuthority(HUBUSER_UPDATE.name())
                            .requestMatchers(POST, "/comments/*/evaluations/**").
                                hasAnyAuthority(HUBUSER_CREATE.name())
                            .requestMatchers(DELETE, "/comments/*/evaluations/**").
                                hasAnyAuthority(HUBUSER_DELETE.name())
                                /* CHAMPIONS */
                                // Roles
                            .requestMatchers("/champions/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name(), HUBUSER.name())
                                // Authority
                            .requestMatchers(GET, "/champions/**").
                                hasAnyAuthority(HUBUSER_READ.name())
                                /* RSO */
                                // Roles
                            .requestMatchers("/rso/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name(), HUBUSER.name())
                                // Authority
                            .requestMatchers(GET, "/rso/**").
                                hasAnyAuthority(HUBUSER_READ.name())
                            /* ROLE: MODERATORS */
                                // Roles
                            .requestMatchers("/moderators/**").
                                hasAnyRole(ADMIN.name(), MODERATOR.name())
                                // Authority
                            .requestMatchers(PUT, "/moderators/**").
                                hasAnyAuthority(MODERATOR_UPDATE.name())
                            .requestMatchers(DELETE, "/moderators/**").
                                hasAnyAuthority(MODERATOR_DELETE.name())
                            /* ROLE: ADMINS */
                                // Roles
                            .requestMatchers("/admins/**").
                                hasAnyRole(ADMIN.name())
                                // Authority
                            .requestMatchers(PUT, "/admins/**").
                                hasAnyAuthority(ADMIN_UPDATE.name())
                            .requestMatchers(DELETE, "/admins/**").
                                hasAnyAuthority(ADMIN_DELETE.name())
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}

