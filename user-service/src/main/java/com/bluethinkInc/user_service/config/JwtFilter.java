package com.bluethinkInc.user_service.config;

import com.bluethinkInc.user_service.service.JwtService;
import com.bluethinkInc.user_service.service.impl.MyUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    ApplicationContext context;
    public JwtFilter(JwtService jwtService,ApplicationContext context){
        this.jwtService = jwtService;
        this.context = context;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer juvjvfu34r9ufhcuu9u9ir09diciuh89iu29riidciuj09i2rdiio.i9r2jcnijioi.ifw9icnijioj43
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);

            username = jwtService.extractUsername(token);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            String type = jwtService.extractTokenType(token);
            if(!"access_token".equals(type)){
                throw new RuntimeException("Invalid token type");
            }
            UserDetails userDetails = context.getBean(MyUserDetailsServiceImpl.class)
                    .loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails)){
                String role = jwtService.extractRole(token);
                List<SimpleGrantedAuthority>authorities =
                        List.of(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
