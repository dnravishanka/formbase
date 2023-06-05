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
    // =========defautlPasswordEncorder is not safe and is to be deprecated=====

    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("manager")
                .password("1234")
                .roles("MANAGER")
                .build();
        UserDetails user3 = User.withDefaultPasswordEncoder()
                .username("user")
                .password("123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1,user2,user3);


    }*/

    //-------------PasswordEncoder for better Encoding --------
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder().username("admin")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2 = User.builder().username("manager")
//                .password(passwordEncoder().encode("1234"))
//                .roles("MANAGER")
//                .build();
//        UserDetails user3 = User.builder().username("nadun")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1,user2,user3);
//    }


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
                .formLogin((form)->form
                        .loginPage("/login").permitAll())
                .logout((logout)->logout.permitAll()
                        .logoutSuccessUrl("/home"))
                .rememberMe().tokenValiditySeconds(60).key("mytoken");


//        httpSecurity.csrf().disable();
        return httpSecurity.build();

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
