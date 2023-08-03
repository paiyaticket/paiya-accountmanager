package events.paiya.accountmanager.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorize -> authorize
                        // .requestMatchers("/financial-accounts")
                        // .hasAuthority("SCOPE_paiya_amrs_user_financials")
                        // .requestMatchers("/users/**")
                        // .hasAuthority("SCOPE_paiya_amrs_user_info")
                        .requestMatchers("/users/**")
                        .hasAuthority("SCOPE_paiya_amrs_user_alldata")
                        .anyRequest()
                        .authenticated())
                        .httpBasic(withDefaults());

        return http.build();
    }
}
