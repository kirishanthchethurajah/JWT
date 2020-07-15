package com.kira.security.benutzerlogin.kontroller;

import com.kira.security.benutzerlogin.model.AuthRequest;
import com.kira.security.benutzerlogin.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class BenutzerLoginController {
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public BenutzerLoginController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public String zeigenNachrichtenAn(){
        return "no idea";
    }

    @PostMapping("/authenticate")
    public String erzeugenToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getBenutzerName(),
                    authRequest.getBenutzerKennwort()));
        } catch (BadCredentialsException e){
            throw new Exception("ungültig  BenutzerName oder Kennwort.", e);
        }
        return jwtUtil.erzeugenToken(authRequest.getBenutzerName());
    }

    @GetMapping("/verwalter")
    public String zeigenAdminNachrichtenAn(){
        return "Hallo Admin";
    }

    @GetMapping("/benutzer")
    public String zeigenNormalBenutzerNachrichtenAn(){
        return "Hallo User";
    }
}
