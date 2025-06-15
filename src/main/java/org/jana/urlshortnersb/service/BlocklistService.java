package org.jana.urlshortnersb.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BlocklistService {

    // List of known malicious domains/IPs (in production this would come from a database)
    private static final List<String> BLOCKED_DOMAINS = Arrays.asList(

        "phishingsite.net",
        "adultsite.org"
    );

    public boolean isBlocked(String domain) {
        // Remove www prefix if present
        String cleanDomain = domain.startsWith("www.") ? domain.substring(4) : domain;
        return BLOCKED_DOMAINS.contains(cleanDomain);
    }
}
