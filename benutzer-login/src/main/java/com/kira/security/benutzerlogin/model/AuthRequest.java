package com.kira.security.benutzerlogin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {
    private String benutzerName;
    private String benutzerKennwort;
}
