package com.traymee.traymeeback.config.init;

import com.traymee.traymeeback.db.entity.User;
import com.traymee.traymeeback.db.enumeration.Role;
import com.traymee.traymeeback.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        UserDetails user;
        try {
            user = userService.loadUserByUsername("user");
            log.info("Test user is already in DB");
        } catch (UsernameNotFoundException e) {
            user = userService.save(User.builder()
                    .username("user")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList(Role.USER))
                    .build()
            );
            log.info("Created user {user, password}");
        }

        try {
            userService.loadUserByUsername("admin");
            log.info("Test admin is already in DB");
        } catch (UsernameNotFoundException e) {
            userService.save(User.builder()
                    .username("admin")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList(Role.ADMIN))
                    .build()
            );
            log.info("Created admin {admin, password}");
        }
    }
}
