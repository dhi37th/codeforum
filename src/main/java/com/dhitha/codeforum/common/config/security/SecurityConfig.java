package com.dhitha.codeforum.common.config.security;

import com.dhitha.codeforum.common.component.EncryptionUtil;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {
  @Autowired SecurityUserDetailService securityUserDetailService;

  @Autowired EncryptionUtil encryptionUtil;

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
  /** Manage security calls for front end */
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
          .antMatchers("/h2-console/**")
          .permitAll()
          .and()
          .headers()
          .frameOptions()
          .disable();

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
