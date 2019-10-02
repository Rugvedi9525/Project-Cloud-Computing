package edu.neu.coe.csye6225.cloudnativeapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {

            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }


    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().ignoringAntMatchers("/createAccount");
        http.csrf().ignoringAntMatchers("/login");
        http.csrf().ignoringAntMatchers("/upload");
        http.csrf().ignoringAntMatchers("/profilePic");
        http.csrf().ignoringAntMatchers("/offlineProfile/*");
        http.csrf().ignoringAntMatchers("/resetPassword");

        http
                .authorizeRequests()
                    .antMatchers("/createAccount").permitAll()
                .antMatchers("/profilePic").permitAll()
                .antMatchers("/offlineProfile/*").permitAll()
                .antMatchers("/resetPassword").permitAll()
                    .anyRequest().
                authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()

                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();


    }
}
