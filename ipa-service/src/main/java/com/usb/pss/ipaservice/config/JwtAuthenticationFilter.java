package com.usb.pss.ipaservice.config;

import com.usb.pss.ipaservice.inventory.dto.custom.JwtUserDto;
import com.usb.pss.ipaservice.inventory.repository.TokenRepository;
import com.usb.pss.ipaservice.inventory.service.JwtService;
import com.usb.pss.ipaservice.inventory.service.LogoutService;
import com.usb.pss.ipaservice.inventory.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final LogoutService logoutService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var isTokenBlacklisted = logoutService.checkIfBlacklisted(jwt);

//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

//            if (jwtService.isTokenValid(jwt, userDetails) && !isTokenBlacklisted) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            } else if (!jwtService.isTokenValid(jwt, userDetails)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("text/plain");
//                response.getWriter().write("Invalid jwt");
//                return;
//            } else if (isTokenBlacklisted) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("text/plain");
//                response.getWriter().write("Blacklisted token");
//                return;
//            }

            JwtUserDto jwtUserDto = this.userDetailServiceImpl.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, jwtUserDto) && !isTokenBlacklisted) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        jwtUserDto, null, jwtUserDto.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else if (!jwtService.isTokenValid(jwt, jwtUserDto)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Invalid jwt");
                return;
            } else if (isTokenBlacklisted) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Blacklisted token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
