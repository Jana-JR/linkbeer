package org.jana.urlshortnersb.filter;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RateLimitingFilterTest {
    private RateLimitingFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private PrintWriter printWriter;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() {
        filter = new RateLimitingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        // Mock getWriter to avoid NullPointerException
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(printWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void allowsRequestsUnderLimit() throws ServletException, IOException {
        when(request.getRemoteAddr()).thenReturn("1.2.3.4");
        for (int i = 0; i < 100; i++) {
            filter.doFilterInternal(request, response, filterChain);
        }
        verify(filterChain, times(100)).doFilter(request, response);
        verify(response, never()).setStatus(429);
    }

    @Test
    void blocksRequestsOverLimit() throws ServletException, IOException {
        when(request.getRemoteAddr()).thenReturn("5.6.7.8");
        for (int i = 0; i < 100; i++) {
            filter.doFilterInternal(request, response, filterChain);
        }
        // 101st request should be blocked
        filter.doFilterInternal(request, response, filterChain);
        verify(response).setStatus(429);
        verify(response).getWriter();
        printWriter.flush();
        assertTrue(stringWriter.toString().contains("Too many requests"));
    }
}
