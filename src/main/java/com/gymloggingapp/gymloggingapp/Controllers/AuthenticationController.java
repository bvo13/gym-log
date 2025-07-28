package com.gymloggingapp.gymloggingapp.Controllers;

import com.gymloggingapp.gymloggingapp.Service.AuthenticationService;
import com.gymloggingapp.gymloggingapp.config.JwtService;
import com.gymloggingapp.gymloggingapp.util.AuthenticationRequest;
import com.gymloggingapp.gymloggingapp.util.AuthenticationResponse;
import com.gymloggingapp.gymloggingapp.util.RegistrationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest reg, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.register(reg);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", authenticationResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authenticationResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add(HttpHeaders.SET_COOKIE,accessCookie.toString());
            httpHeaders.add(HttpHeaders.SET_COOKIE,refreshCookie.toString());
        }).build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest auth, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(auth);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", authenticationResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authenticationResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add(HttpHeaders.SET_COOKIE,accessCookie.toString());
            httpHeaders.add(HttpHeaders.SET_COOKIE,refreshCookie.toString());
        }).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        ResponseCookie accessCookie = ResponseCookie.from("access_token","")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .secure(true)
                .sameSite("Strict")
        .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token","")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .secure(true)
                .sameSite("Strict")
        .build();
        authenticationService.logout();

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add(HttpHeaders.SET_COOKIE,accessCookie.toString());
            httpHeaders.add(HttpHeaders.SET_COOKIE,refreshCookie.toString());
        }).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response, HttpServletRequest request){

        AuthenticationResponse authenticationResponse = authenticationService.refresh(request);

        ResponseCookie accessCookie = ResponseCookie.from("access_token", authenticationResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authenticationResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add(HttpHeaders.SET_COOKIE,accessCookie.toString());
            httpHeaders.add(HttpHeaders.SET_COOKIE,refreshCookie.toString());
        }).build();

    }
}
