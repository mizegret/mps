package com.mizegret.mps.mps_shared.filters;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestIdFilter extends OncePerRequestFilter{
    private static final String HEADER = "X-Request-Id";
    private static final String MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        final String requestId = Optional.ofNullable(request.getHeader(HEADER)).filter(rId -> !rId.isBlank())
                .orElse(UUID.randomUUID().toString());

        MDC.put(MDC_KEY, requestId);
        response.setHeader(HEADER, requestId);
        try{
            filterChain.doFilter(request, response);
        } finally{
            MDC.remove(MDC_KEY);
        }
    }
}
