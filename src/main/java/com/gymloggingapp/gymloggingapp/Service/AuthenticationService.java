package com.gymloggingapp.gymloggingapp.Service;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Repositories.RefreshTokenRepository;
import com.gymloggingapp.gymloggingapp.Repositories.UserRepository;
import com.gymloggingapp.gymloggingapp.config.JwtService;
import com.gymloggingapp.gymloggingapp.util.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenExtractor tokenExtractor;

    public Long getCurrentUserId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null||!authentication.isAuthenticated()){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserEntity){
            return ((UserEntity)principal).getId();
        }
        return null;
    }

    public AuthenticationResponse register(RegistrationRequest request){
        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user.setRole(Role.USER);
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        String accessToken = jwtService.generateToken(claims,user,1000*60*15);
        String refreshToken = jwtService.generateToken(claims,user,1000*60*60*24*7);
        RefreshToken token = new RefreshToken();
        token.setJwtToken(refreshToken);
        token.setUser(user);
        token.setExpirationDate(Instant.now().plus(Duration.ofDays(7)));
        refreshTokenRepository.save(token);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        String accessToken = jwtService.generateToken(claims,user, 1000*60*15);
        String refreshToken = jwtService.generateToken(claims,user,1000*60*60*24*7);
        RefreshToken token = new RefreshToken();
        token.setJwtToken(refreshToken);
        token.setUser(user);
        token.setExpirationDate(Instant.now().plus(Duration.ofDays(7)));
        refreshTokenRepository.save(token);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public void logout(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserEntity){
            refreshTokenRepository.deleteAllByUser((UserEntity)authentication.getPrincipal());
        }
        else{
            throw new RuntimeException("error in logging out");
        }
    }
    public AuthenticationResponse refresh(HttpServletRequest request){
        String refreshCookie = tokenExtractor.extractToken(request, "refresh_token");
        String email = jwtService.extractUserEmail(refreshCookie);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));

       RefreshToken databaseToken = refreshTokenRepository.findFirstByUserOrderByExpirationDateDesc(user).orElseThrow(
               ()->new RuntimeException("token not found"));
       if(databaseToken==null){
           throw new RuntimeException("token not found in database");
       }
       if(!refreshCookie.equals(databaseToken.getJwtToken())){
           throw new RuntimeException("tokens are not the same");
       }
         if(!jwtService.isTokenValid(refreshCookie,user)){
             throw new RuntimeException("not a valid token");
         }
        String accessToken = jwtService.generateToken(user, 1000*60*15);
        String refreshToken = jwtService.generateToken(user, 1000*60*60*24*7);
        refreshTokenRepository.deleteAllByUser(user);
           RefreshToken token = new RefreshToken();
           token.setJwtToken(refreshToken);
           token.setUser(user);
           token.setExpirationDate(Instant.now().plus(Duration.ofDays(7)));
           refreshTokenRepository.save(token);

        return new AuthenticationResponse(accessToken,refreshToken);


    }
}
