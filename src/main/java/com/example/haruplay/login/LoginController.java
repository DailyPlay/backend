package com.example.haruplay.login;

import com.example.haruplay.global.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginEntity loginRequest) {
        log.info("Received login request for user: {}", loginRequest.getUserEmail());

        // snsType check -> 분기처리 예정
        // userEmail = user id

        // guest인 경우
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserEmail(),  // 클라이언트로부터 받은 ID
                        null  // 패스워드를 사용하지 않으므로 null
                )
        );

        // 인증 후 SecurityContext에 인증 정보를 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(String.valueOf(loginRequest.getUserEmail()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok().headers(headers).body("Login successful");
    }
}
