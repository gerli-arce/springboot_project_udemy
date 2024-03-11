package com.yon.backend.cartapp.backendcartapp.auth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.yon.backend.cartapp.backendcartapp.auth.TokenJwtConfig.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFiter extends BasicAuthenticationFilter {

    public JwtValidationFiter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            String header = request.getHeader(HEADER_AUTORITATION);
            if (header == null || !header.startsWith(PREFIX_TOKEN)) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.replace(PREFIX_TOKEN, "");

            byte[] tokenDecodeBytes = Base64.getDecoder().decode(token);
            String tokenDecode = new String(tokenDecodeBytes);

            String[] tokenArr = tokenDecode.split(".");

            String secret = tokenArr[0];
            String username = tokenArr[1];

            if(SECRET_KEY.equals(secret)){
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request, response);
            }else{
                Map<String, String> body = new HashMap<>();
                body.put("message", "El token JWT no es valido!");

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(203);
                response.setContentType("application/json");

            }


        }

}
