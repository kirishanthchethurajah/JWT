package com.kira.security.benutzerlogin.config;

import com.kira.security.benutzerlogin.service.CustomBenutzerService;
import com.kira.security.benutzerlogin.filter.JwtRequestFilter;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomBenutzerService customBenutzerService;
    private JwtRequestFilter jwtRequestFilter;
    // for JDBC authentication
//    private DataSource dataSource;
//
//    public LoginSecurityConfig(CustomBenutzerService customBenutzerService, JwtRequestFilter jwtRequestFilter, DataSource dataSource) {
//        this.customBenutzerService = customBenutzerService;
//        this.jwtRequestFilter = jwtRequestFilter;
//        this.dataSource = dataSource;
//    }


    public LoginSecurityConfig(CustomBenutzerService customBenutzerService, JwtRequestFilter jwtRequestFilter) {
        this.customBenutzerService = customBenutzerService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
// Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //In memory authentication
//        auth.inMemoryAuthentication()
//                .withUser("kira")
//                .password("noIdea")
//                .roles("ADMIN")
//                .and().withUser("Elon")
//                .password("Musk")
//                .roles("USER");
        //jdbc
    /*    auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select * from users where username = ?")
                .authoritiesByUsernameQuery("select * from authorities where username = ?");
                *//*.withDefaultSchema()
                .withUser(
                        User.withUsername("kira")
                        .password("noIdea")
                        .roles("ADMIN"))
                .withUser(
                        User.withUsername("Elon")
                        .password("Musk")
                        .roles("ADMIN"));*/

        // JWT
        auth.userDetailsService(customBenutzerService);


        //LDAP
        /*auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//    Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cross site  request forgery
        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate","/","/errors","/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN","USER")
                .antMatchers("/").permitAll()
                .and().formLogin();*/
        /*http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**","/authenticate").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login();*/



        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
