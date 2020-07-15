package com.kira.security.benutzerlogin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secret = "Geheimnis";
    public String erzeugenToken(String benutzerName) {
        Map<String, Object> anspruch = new HashMap<>();
        return erstellenToken(anspruch, benutzerName);
    }

    private String erstellenToken(Map<String, Object> anspruch, String benutzerName) {
        return Jwts.builder()
                .setClaims(anspruch)
                .setSubject(benutzerName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + 1000 * 36000)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean verifizierenToken(String token, UserDetails userDetails){
        final String benutzerName = extraktBenutzerName(token);
        return (benutzerName.equals(userDetails.getUsername()) && !istTokenAbgelaufen(token));
    }

    public String extraktBenutzerName(String token) {
        return extractAnspruch(token, Claims::getSubject);
    }

    private boolean istTokenAbgelaufen(String token) {
        return extraktVerfallszeit(token).before(new Date());
    }

    private Date extraktVerfallszeit(String token) {
        return extractAnspruch(token, Claims::getExpiration);
    }

    public <T> T extractAnspruch(String token, Function<Claims, T> anspruchResolver){
        final Claims anspruch = extraktAllClaim(token);
        return anspruchResolver.apply(anspruch);
    }

    private Claims extraktAllClaim(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
