package com.bss.restaurant.security;

import com.bss.restaurant.dao.UserRepository;
import com.bss.restaurant.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() ->
                        new RestaurantNotFoundException("User not found")
                );
        var userDetails = new UserDetailsImpl(user);
        return userDetails;
    }
}
