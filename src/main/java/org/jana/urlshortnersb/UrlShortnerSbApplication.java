package org.jana.urlshortnersb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UrlShortnerSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortnerSbApplication.class, args);
    }
}
