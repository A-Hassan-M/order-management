package com.qeema.practicaltest.ordermanagement.domain.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.qeema.practicaltest.ordermanagement.domain.users.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.qeema.practicaltest.ordermanagement.domain.users.repos.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email);
    }

    public User findByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with this email: %s".formatted(email)));
    }
}
