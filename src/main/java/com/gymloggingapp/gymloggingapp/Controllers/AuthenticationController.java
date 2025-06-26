package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.util.AuthenticationRequest;
import com.gymloggingapp.gymloggingapp.util.AuthenticationResponse;
import com.gymloggingapp.gymloggingapp.util.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest reg){
        return ResponseEntity.ok(authenticationService.register(reg));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest auth){
        return ResponseEntity.ok(authenticationService.authenticate(auth));
    }

}
