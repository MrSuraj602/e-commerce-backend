package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.entity.User;
import com.MrSuraj.eco.ecommerce.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomeUserServiceImplementationTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomeUserServiceImplementation userDetailsService;

    @Test
    void shouldLoadAdminAuthorityFromStoredRole() {
        User user = user("admin@example.com", "ADMIN");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());

        assertEquals("ROLE_ADMIN", details.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void shouldDefaultMissingRoleToCustomer() {
        User user = user("customer@example.com", null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        UserDetails details = userDetailsService.loadUserByUsername(user.getEmail());

        assertEquals("ROLE_CUSTOMER", details.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void shouldRejectUnknownEmail() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("missing@example.com"));
    }

    private User user(String email, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("encoded-password");
        user.setRole(role);
        return user;
    }
}