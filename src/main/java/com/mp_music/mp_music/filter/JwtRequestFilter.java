package com.mp_music.mp_music.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mp_music.mp_music.service.impl.MyUserDetailsService;
import com.mp_music.mp_music.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // try {
        // String token = extractJwtFromRequest(request);

        // if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
        // String username = jwtUtil.extractUsername(token);
        // if (username != null &&
        // SecurityContextHolder.getContext().getAuthentication() == null) {
        // UserDetails userDetails =
        // this.userDetailsService.loadUserByUsername(username);

        // if (jwtUtil.validateToken2(token, userDetails)) {
        // UsernamePasswordAuthenticationToken userPassAuthToken = new
        // UsernamePasswordAuthenticationToken(
        // userDetails, null, userDetails.getAuthorities());

        // userPassAuthToken.setDetails(new
        // WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
        // }
        // }
        // }

        // } catch (ExpiredJwtException ex) {
        // String isRefreshToken = request.getHeader("isRefreshToken");

        // String requestURL = request.getRequestURL().toString();
        // // allow for Refresh Token creation if following conditions are true.
        // if (isRefreshToken != null && isRefreshToken.equals("true") &&
        // requestURL.contains("refreshtoken")) {
        // allowForRefreshToken(ex, request);
        // } else
        // request.setAttribute("exception", ex);

        // } catch (BadCredentialsException ex) {
        // request.setAttribute("exception", ex);
        // } catch (Exception ex) {
        // System.out.println(ex);
        // }

        /////////////////////////////////

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken2(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
            }
        }

        chain.doFilter(request, response);
    }

    ///////////////////////////////////////

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());
    }

}
