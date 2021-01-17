package com.traymee.traymeeback.web.controller;

import com.traymee.traymeeback.config.security.jwt.JwtTokenProvider;
import com.traymee.traymeeback.db.entity.User;
import com.traymee.traymeeback.db.enumeration.Role;
import com.traymee.traymeeback.db.repository.UserRepository;
import com.traymee.traymeeback.exception.UserRegistrationException;
import com.traymee.traymeeback.service.impl.UserService;
import com.traymee.traymeeback.web.model.auth.AuthenticationRequestModel;
import com.traymee.traymeeback.web.model.auth.AuthenticationResponseModel;
import com.traymee.traymeeback.web.model.auth.RegistrationRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequestModel data) {

        try {
            String username = data.getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            Long id = user.getId();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationResponseModel rersponseModel = new AuthenticationResponseModel(id, token, user.getRoles().get(0).name());

            return ok(rersponseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegistrationRequestModel data) throws UserRegistrationException {

        try {
            userService.loadUserByUsername(data.getUsername());
        } catch (UsernameNotFoundException e) {
            if (!data.getPassword().equals(data.getRepeatPassword())) {
                throw new UserRegistrationException("Passwords bust be the same");
            }

            User user = new User();

            user.setUsername(data.getUsername());
            user.setPassword(passwordEncoder.encode(data.getPassword()));

            List<Role> roles = new ArrayList<>();
            roles.add(Role.USER);
            user.setRoles(roles);

            userService.save(user);
            return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
        }
        throw new UserRegistrationException("User with this credentials already exists");
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }

}
