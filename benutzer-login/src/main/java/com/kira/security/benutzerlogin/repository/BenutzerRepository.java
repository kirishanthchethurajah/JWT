package com.kira.security.benutzerlogin.repository;

import com.kira.security.benutzerlogin.model.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Benutzer findByBenutzerName(String benutzerName);
}
