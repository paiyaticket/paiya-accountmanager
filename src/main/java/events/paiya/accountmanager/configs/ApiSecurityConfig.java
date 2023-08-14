package events.paiya.accountmanager.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("security")
public class ApiSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v1/financial-accounts/**").hasAuthority("SCOPE_paiya_amrs/user_financials")
                        .requestMatchers("/v1/users/**").hasAuthority("SCOPE_paiya_amrs/user_info")
                        .requestMatchers("/v1/event-organizers/**").hasAuthority("SCOPE_paiya_amrs/user_info")
                        .requestMatchers("/v1/**").hasAuthority("SCOPE_paiya_amrs/alldata")
                        .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return token -> null;
    }
}