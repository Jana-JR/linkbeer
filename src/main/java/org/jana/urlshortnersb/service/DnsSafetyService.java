package org.jana.urlshortnersb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class DnsSafetyService {

    private static final Logger logger = LoggerFactory.getLogger(DnsSafetyService.class);

    private final RestTemplate restTemplate;

    // Cloudflare Family DoH endpoint - blocks malware + adult content
    private static final String CLOUDFLARE_FAMILY_DOH_URL = "https://family.cloudflare-dns.com/dns-query";

    public DnsSafetyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isDomainSafe(String domain) {
        try {
            boolean isBlocked = queryCloudflareFamilyDns(domain);
            if (isBlocked) {
                logger.info("Domain {} is blocked by Cloudflare Family DNS", domain);
            }
            return !isBlocked;
        } catch (Exception e) {
            logger.error("Cloudflare DoH query failed for {}: {}", domain, e.getMessage());
            return true; // Fallback: assume safe on failure
        }
    }

    private boolean queryCloudflareFamilyDns(String domain) {
        try {
            String url = String.format("%s?name=%s&type=A", CLOUDFLARE_FAMILY_DOH_URL, domain);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.parseMediaType("application/dns-json")));
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return analyzeDnsResponse(response.getBody());
            } else {
                logger.warn("Cloudflare DoH returned status: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("DoH query error: {}", e.getMessage());
            return false;
        }
    }

    private boolean analyzeDnsResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode answer = root.get("Answer");

            if (answer != null && answer.isArray()) {
                for (JsonNode item : answer) {
                    if (item.path("type").asInt() == 1) { // A record
                        String data = item.path("data").asText();
                        if ("0.0.0.0".equals(data)) {
                            return true; // blocked by Cloudflare Family DNS
                        }
                    }
                }
            }
            return false;
        } catch (IOException e) {
            logger.error("Failed to parse DNS response: {}", e.getMessage());
            return false;
        }
    }
}
