package com.sky7th.devtimeline.api.security;

import com.sky7th.devtimeline.api.user.CustomUserDetails;
import com.sky7th.devtimeline.core.domain.user.service.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInternalService userInternalService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(userInternalService.findByEmail(email));
    }
}