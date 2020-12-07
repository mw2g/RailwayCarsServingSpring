package com.browarna.railwaycarsserving.config;

import com.browarna.railwaycarsserving.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/api/delivery/**",
                        "/api/memo/**",
                        "/api/statement/**",
                        "/api/wagon-type/**",
                        "/api/wagon-group/**",
                        "/api/customer/**",
                        "/api/cargo-type/**",
                        "/api/cargo-operation/**",
                        "/api/base-rate/**",
                        "/api/index-to-base-rate/**",
                        "/api/penalty/**",
                        "/api/tariff/**",
                        "/api/tariff-type/**",
                        "/api/time-norm/**",
                        "/api/time-norm-type/**").hasRole("WATCH")
                .antMatchers(HttpMethod.DELETE,
                        "/api/delivery/**",
                        "/api/memo/**",
                        "/api/statement/**").hasRole("ADMIN")
                .antMatchers(
                        "/api/delivery/**",
                        "/api/memo/**",
                        "/api/statement/**").hasRole("USER")
                .antMatchers(
                        "/api/admin/**",
                        "/api/wagon-type/**",
                        "/api/wagon-group/**",
                        "/api/customer/**",
                        "/api/cargo-type/**",
                        "/api/cargo-operation/**",
                        "/api/base-rate/**",
                        "/api/index-to-base-rate/**",
                        "/api/penalty/**",
                        "/api/tariff/**",
                        "/api/tariff-type/**",
                        "/api/time-norm/**",
                        "/api/time-norm-type/**").hasRole("ADMIN")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
//            .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint(){
//        return new CustomAuthenticationEntryPoint();
//    }
}
