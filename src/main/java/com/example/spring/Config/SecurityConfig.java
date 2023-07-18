package com.example.spring.Config;


import com.example.spring.Auth.ApplicationUserService;
import com.example.spring.Jwt.JwtAuthentication;
import com.example.spring.Jwt.JwtConfig;
import com.example.spring.Jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

import static com.example.spring.Config.UserRoles.*;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService
            ,SecretKey secretKey,JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               //.and()
               .csrf().disable()
               .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .addFilter(new JwtAuthentication(authenticationManager(), jwtConfig, secretKey))
               .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtAuthentication.class)
               .authorizeRequests()
               .antMatchers("/","index").permitAll()
               .antMatchers("/api/v1/**").hasRole(USER.name())
               .anyRequest()
               .authenticated();
//               .and()
//               .formLogin()
//                   .loginPage("/login").permitAll()
//                   .defaultSuccessUrl("/course",true)
//                   .passwordParameter("password")
//                   .usernameParameter("username")
//               .and()
//               .rememberMe()
//                   .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                   .key("something")
//                   .rememberMeParameter("remember-me")
//               .and()
//               .logout()
//                   .logoutUrl("/logout")
//                   .clearAuthentication(true)
//                   .invalidateHttpSession(true)
//                   .deleteCookies("JSESSIONID","remember-me")
//                   .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
