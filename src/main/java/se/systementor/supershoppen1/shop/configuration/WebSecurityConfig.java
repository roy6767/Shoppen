package se.systementor.supershoppen1.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import se.systementor.supershoppen1.shop.services.ShopUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    private ShopUserDetailsService userDetailsService;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,PasswordEncoder passwordEncoder) 
      throws Exception {
        auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
        //   .inMemoryAuthentication()
        //   .passwordEncoder(passwordEncoder)
        //   .withUser("user@user.se")
        //   .password(passwordEncoder.encode("stefan"))
        //   .roles("USER")
        //   .and()
        //   .withUser("admin@user.se")
        //   .password(passwordEncoder.encode("stefan"))
        //   .roles("ADMIN");
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/*", "/css/**", "/images/**", "/lib/**", "/scripts/**", "/static/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // You can use the same login page if needed
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                );
        return http.build();
    }


    public WebSecurityConfig() {
        super();
    }




}