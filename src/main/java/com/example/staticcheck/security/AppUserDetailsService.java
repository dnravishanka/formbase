package com.example.staticcheck.security;


import com.example.staticcheck.model.Users;
import com.example.staticcheck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
 @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users byUserName = this.userRepository.findByUserName(username);
        AppUserDetails appUserDetails = new AppUserDetails(byUserName);

        return appUserDetails;

    }
}
