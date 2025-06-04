package org.jana.urlshortnersb.security.jwt;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse() {}

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
