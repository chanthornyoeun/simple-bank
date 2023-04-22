package com.example.bank.auth;

import com.example.bank.models.User;
import com.example.bank.services.JwtService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            User user = this.authenticationService.login(loginDTO);
            String token = jwtService.generateToken(user);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("user", user);
            data.put("token", token);
            ResponseDTO res = ResponseUtil.object(data, true, "Logged In");
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ResponseUtil.object(null, false, ex.getMessage()));
        }
    }

}
