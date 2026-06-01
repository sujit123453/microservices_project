package com.bluethinkInc.api_gateway.filter;

import com.bluethinkInc.api_gateway.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        System.out.println("Request URI = " + request.getRequestURI());

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            try{
                username = jwtService.extractUsername(token);
            }catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Invalid token.please login again");
                return;
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            String type = jwtService.extractTokenType(token);

            if(!"access_token".equals(type)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Wrong token type");
            }
            String role = jwtService.extractRole(token);
            List<SimpleGrantedAuthority>authorities =
                    List.of(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username,null,authorities);
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request,response);
    }
}
