package com.spring.data.jpa;

import com.spring.data.jpa.service.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
private JpaUserDetailService userDetailsService;
    @Bean
public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        }
@Autowired
private BCryptPasswordEncoder passwordEncoder;


@Override
protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/","/css/**","/vendor/**","/img/**").permitAll()
        .antMatchers("/*").authenticated()
        .and()
        .formLogin().loginPage("/login")
        .permitAll()
        .and().logout().permitAll();
        }

@Autowired
public void configurerGlobal(AuthenticationManagerBuilder buil)throws Exception{

        buil.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        }

}