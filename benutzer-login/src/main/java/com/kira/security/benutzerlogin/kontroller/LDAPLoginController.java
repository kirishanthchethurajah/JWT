package com.kira.security.benutzerlogin.kontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class LDAPLoginController {
    @GetMapping("/secure")
    public String ldapMethod(){
        return "Hallo";
    }
}
