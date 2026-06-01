package com.bluethinkInc.api_gateway.service.impl;
import com.bluethinkInc.api_gateway.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private  String secretKey;

    public SecretKey keyword(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String extractUsername(String token) {
        System.out.println("extract username");
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractTokenType(String token) {
        return extractClaim(token, claims->claims.get("type",String.class));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        System.out.println("Username from Token: " + userName);
        System.out.println("Username from DB: " + userDetails.getUsername());
        return (userName.equals(userDetails.getUsername()) &&  !isTokenExpired(token));
    }

    @Override
    public String extractRole(String token) {
        System.out.println("extract role");
        return extractAllClaims(token).get("role",String.class);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(keyword())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException e){
            throw new RuntimeException("Expired token, please login again");
        }catch(JwtException e){
            throw new RuntimeException("Invalid token!!");
        }
    }
}

