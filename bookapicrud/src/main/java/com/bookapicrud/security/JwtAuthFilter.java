package com.bookapicrud.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookapicrud.model.Security.UserDetailsImpl;
import com.bookapicrud.security.Tokens.BearerToken;
import com.bookapicrud.service.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final BearerToken bearerTokenService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private List<String> excludePaths = Arrays.asList(
        "/api/auth/test",              
        "/api/auth/register",
        "/api/auth/activateAccount",
        "/api/auth/login",
        "/api/auth/refreshToken",
        "/api/auth/requestPassword",
        "/api/auth/recoverPassword",
        "/api/users/test",
        "/api/books/test",
       "/api/books/title",
        "/api/books/author",
       "/api/books/list"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);

        try{

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }
            else if(bearerTokenService.isTokenExpired(authHeader.replace("Bearer ", ""))){

                Map<String, String> dataResponse = new HashMap<>();
                dataResponse.put("\"status\"", "\"error\"");
                dataResponse.put("\"message\"", "\"JWT error\"");

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().print(dataResponse.toString().replaceAll("=", ":"));
                response.getWriter().flush();

                return;
            }
        }catch(SignatureException e){
                Map<String, String> dataResponse = new HashMap<>();
                dataResponse.put("\"status\"", "\"error\"");
                dataResponse.put("\"message\"", "\"JWT error\"");

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setContentType("application/json");
                response.getWriter().print(dataResponse.toString().replaceAll("=", ":"));
                response.getWriter().flush();            
            return;
        }

        String requestToken = authHeader.replace("Bearer ", "");

        UserDetailsImpl userDetails = new UserDetailsImpl(null);
        try{        
            
            userDetails = (UserDetailsImpl)userDetailsServiceImpl.loadUserByUsername((String) bearerTokenService.extractClaim(requestToken, Claims.SUBJECT, new String()));


        }catch(UsernameNotFoundException e){
            response.setStatus(400);
            response.setContentType("application/json");
            response.getOutputStream().write(Map.of("'status'", "'error'", "message", e.getMessage()).toString().getBytes());
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null && bearerTokenService.isTokenValid(requestToken) && userDetails != null){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(bearerTokenService.extractClaim(requestToken, Claims.SUBJECT, new String()), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        if(request.getMethod().equals("OPTIONS")){return true;}
        return excludePaths.contains(request.getServletPath());
    }
    
}
