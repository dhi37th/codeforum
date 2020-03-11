package com.dhitha.codeforum.common.config.security;

import com.dhitha.codeforum.common.component.EncryptionUtil;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  SecurityUserDetailService securityUserDetailService;

  @Autowired
  EncryptionUtil encryptionUtil;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence rawPassword) {
        try {
          return encryptionUtil.encrypt(rawPassword.toString());
        } catch (GeneralSecurityException e) {
          return null;
        }
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
          return rawPassword.toString().equals(encryptionUtil.decrypt(encodedPassword));
        } catch (GeneralSecurityException e) {
          return false;
        }
      }
    };
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(securityUserDetailService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

//  @Configuration
//  @Order(1)
  public  class ApiSecurityConfig extends WebSecurityConfigurerAdapter{

//    @Autowired
//    ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

    private static final String API_URL = "/api/**";
    private static final String ADMIN = "ADMIN";

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
//      http.antMatcher(API_URL)
//          .authorizeRequests()
//          .antMatchers(HttpMethod.GET, API_URL).hasAnyRole("USER",ADMIN)
//          .antMatchers(HttpMethod.POST, API_URL).hasRole(ADMIN)
//          .antMatchers(HttpMethod.PUT, API_URL).hasRole(ADMIN)
//          .antMatchers(HttpMethod.DELETE, API_URL).hasRole(ADMIN)
//          .and()
//          .httpBasic()
//          .realmName("TCS_RECIPE") // exception caused if not specified : Error creating bean with name 'apiAuthenticationEntryPoint': java.lang.IllegalArgumentException: realmName must be specified
//          .and().exceptionHandling() .authenticationEntryPoint(apiAuthenticationEntryPoint).and().sessionManagement().sessionCreationPolicy(
//          SessionCreationPolicy.STATELESS)
//          .and()
//          .csrf().disable()
//          .formLogin().disable();
    }
  }


  /**
   * Manage security calls for front end
   */
  @Configuration
  public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable();
      http.authorizeRequests().antMatchers(LOGIN_URL).permitAll();
      http.authorizeRequests()
          .antMatchers("h2-console/**")
          .permitAll()
          .and()
          .headers()
          .frameOptions()
          .disable();
      http.authorizeRequests()
          .antMatchers(
              "/v2/api-docs",
              "/configuration/ui",
              "/swagger-resources",
              "/configuration/security",
              "/swagger-ui.html",
              "/webjars/**",
              "/swagger-resources/configuration/ui",
              "/swagge‌​r-ui.html",
              "/swagger-resources/configuration/security")
          .permitAll();

      http.authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .formLogin()
          .loginPage(LOGIN_URL)
          .loginProcessingUrl("/perform_login")
          .defaultSuccessUrl("/home")
          .failureUrl("/login?error=true")
          .usernameParameter("username")
          .passwordParameter("password")
          .and()
          .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl(LOGIN_URL);
    }
  }
}
