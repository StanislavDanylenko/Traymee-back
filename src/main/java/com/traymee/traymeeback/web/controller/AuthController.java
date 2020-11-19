package com.traymee.traymeeback.web.controller;

import com.traymee.traymeeback.config.security.jwt.JwtTokenProvider;
import com.traymee.traymeeback.db.entity.User;
import com.traymee.traymeeback.db.repository.UserRepository;
import com.traymee.traymeeback.web.model.auth.AuthenticationRequestModel;
import com.traymee.traymeeback.web.model.auth.AuthenticationResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequestModel data) {

        try {
            String username = data.getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            Long id = user.getId();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationResponseModel rersponseModel = new AuthenticationResponseModel(id, token);

            return ok(rersponseModel);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
