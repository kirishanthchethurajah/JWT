package com.kira.security.benutzerlogin;

import com.kira.security.benutzerlogin.repository.BenutzerRepository;
import com.kira.security.benutzerlogin.model.Benutzer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BenutzerLoginApplication {

    private BenutzerRepository benutzerRepository;

    public BenutzerLoginApplication(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostConstruct
    public void initBenutzer(){
        List<Benutzer> benutzerListe = Stream.of(Benutzer
                        .builder()
                        .benutzerName("kira")
                        .benutzerKennwort("noIdea")
                        .email("kira@gmail.com")
                        .build(),
                        Benutzer.builder()
                                .benutzerName("Elon")
                                .benutzerKennwort("TSpaceX")
                                .email("Eion@gmail.com")
                                .build()
                )
                .collect(Collectors.toList());
        benutzerRepository.saveAll(benutzerListe);

    }

    public static void main(String[] args) {
        SpringApplication.run(BenutzerLoginApplication.class, args);
    }

}
