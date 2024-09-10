package com.sirh.sirh.security;

import com.sirh.sirh.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author jarellano22
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITE_LIST_URL = {
        "/api/v1/auth/**",
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    };

    //Aplicación de angular
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }
    //Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
        "/vendor/**", "/scss/**", "/include/**", "/plugins/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable() // Deshabilitar CSRF para peticiones basadas en JWT
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sesión sin estado (JWT)
                .and()
                .authorizeRequests()
                .antMatchers(WHITE_LIST_URL).permitAll() // Permitir acceso a la lista blanca de URLs (Swagger, auth, etc.)
                .antMatchers("/generate-token", "/usuarios/, /menu").permitAll() // Permitir acceso a rutas públicas
                .antMatchers("/trabajadores/**").hasAnyRole("REPORTES", "ADMIN", "PERSONAL") // Rutas protegidas
                .anyRequest().authenticated() // Cualquier otra solicitud debe estar autenticada
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler); // Manejo de intentos no autorizados

        // Añadir el filtro JWT antes del filtro de autenticación
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
