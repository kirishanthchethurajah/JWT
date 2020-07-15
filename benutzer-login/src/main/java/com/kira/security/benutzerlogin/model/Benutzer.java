package com.kira.security.benutzerlogin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "BENUTZER")
public class Benutzer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benutzerId;
    private String benutzerName;
    private String benutzerKennwort;
    private String email;
    private String aktiv;
    private String rollen;
}
