package com.kira.security.benutzerlogin.service;

import com.kira.security.benutzerlogin.model.Benutzer;
import com.kira.security.benutzerlogin.repository.BenutzerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomBenutzerService implements UserDetailsService {

    private BenutzerRepository benutzerRepository;

    public CustomBenutzerService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String benutzerName) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByBenutzerName(benutzerName);
        return new User(benutzer.getBenutzerName(),benutzer.getBenutzerKennwort(), new ArrayList<>());
    }
}
