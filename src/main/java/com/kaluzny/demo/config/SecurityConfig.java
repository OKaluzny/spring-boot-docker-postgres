package com.kaluzny.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfig {

    @Value("${spring.security.oauth2.resource-server.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2Configurer -> oauth2Configurer
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwt -> {
                                    // Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
                                    Map<String, Collection<String>> realmAccess = jwt.getClaim("resource_access");
                                    Collection<String> roles = realmAccess.get("roles");
                                    var grantedAuthorities = roles.stream()
                                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                            .collect(Collectors.toList());
                                    return new JwtAuthenticationToken(jwt, grantedAuthorities);
                                }).decoder(jwtDecoder())));
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(jwkSetUri)
                .jwsAlgorithm(SignatureAlgorithm.RS256).build();
    }
}