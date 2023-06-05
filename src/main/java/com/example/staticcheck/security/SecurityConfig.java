package com.example.staticcheck.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private AppUserDetailsService userDetailsService;

    public SecurityConfig(AppUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        //csrf().disable() --> for activate post,delete,put method    / without this only allowed for GET method
        httpSecurity
               // .csrf().disable()
                .authorizeHttpRequests((auth)->auth

                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/manage").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/user").hasAnyRole("ADMIN","MANAGER","USERS")
                        .requestMatchers("/api/v/customer/address").hasRole("ADMIN")
                        .requestMatchers("/api/v/customer/id").hasAnyRole("MANAGER","ADMIN")
                        .anyRequest().authenticated()


                )
//                .formLogin()
//                .loginPage("/login").permitAll();

        /*------------using  spring default convention---------- at login form -->
                                                           1-  <form name="f" th:action="@{/login}" method="post">
                                                           2 - <input type="text"  id="username"  name="username"   >
                                                           3 - <input type="password"  id="password" name="password" >
                                                           4 - <input type="checkbox"  id="remember-me" name="remember-me">
                in these convention spring automatically handle encoding process
        */

                .formLogin((form)->form
                        .loginPage("/login").permitAll())
                .logout((logout)->logout.permitAll()
                        .logoutSuccessUrl("/home"))
                .rememberMe().tokenValiditySeconds(60).key("mytoken");
               httpSecurity.csrf().disable();
        return httpSecurity.build();

          /*---------------using customising naming    -----------login form-----------
                                                            1-  <form name="f" th:action="@{/signin}" method="post">
                                                           2 - <input type="text"  id="myusername"  name="myusername"   >
                                                           3 - <input type="password"  id="mypassword" name="mypassword" >
                                                           4 - <input type="checkbox"  id="myremember-me" name="myremember-me">
                */
              /*  .formLogin((form)->form
                        .loginProcessingUrl("signin")
                        .loginPage("/login").permitAll()
                        .usernameParameter("myusername")
                        .passwordParameter("mypassword"))
                .logout((logout)->logout
                        .permitAll()
                        .logoutSuccessUrl("/home"))
                .rememberMe().tokenValiditySeconds(250).key("mytoken").rememberMeParameter("myremember-me");

//        httpSecurity.csrf().disable();
        return httpSecurity.build();*/

    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/static");
//    }
}
