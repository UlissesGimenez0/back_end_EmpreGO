package com.example.empre_go.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.empre_go.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expString = Long.parseLong(expiracao);

        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);

        Date data = Date.from(
                dataHoraExpiracao
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("email", usuario.getEmail());
        claims.put("perfil", usuario.getPerfil());

        return Jwts.builder()
                .claims(claims)
                .expiration(data)
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = obterClaims(token);

            Date dataExpiracao = claims.getExpiration();

            LocalDateTime data = dataExpiracao
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        } catch (Exception ex) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        Claims claims = obterClaims(token);
        return (String) claims.get("email");
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(chaveAssinatura.getBytes(StandardCharsets.UTF_8));
    }
}