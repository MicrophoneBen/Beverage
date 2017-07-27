package com.github.morotsman.beverage.configuration;

import com.github.morotsman.beverage.model.BeverageUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final BeverageUserRepository bevarageUserRepository;

    public WebSecurityConfig(BeverageUserRepository bevarageUserRepository) {
        this.bevarageUserRepository = bevarageUserRepository;
    }

    @Override  
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v1/user","/v1/user/**").permitAll()
                .anyRequest().authenticated()
                //.anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
    
    @Bean
    public StandardPasswordEncoder encoder() {
        return new StandardPasswordEncoder("dkdsjksdj");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO fix so that it's possible to create a user
        StandardPasswordEncoder encoder = encoder();
        //bevarageUserRepository.save(Arrays.asList(new BeverageUser("user1", encoder.encode("password")), new BeverageUser("user2", encoder.encode("password"))));
        //bevarageUserRepository.findAll().stream().forEach(System.out::println);
        auth.userDetailsService((String username) -> {
            return bevarageUserRepository.findOne(username);
        }).passwordEncoder(encoder);
        
    }

}
