package org.jana.urlshortnersb.service;

import lombok.AllArgsConstructor;
import org.jana.urlshortnersb.dtos.ClickEventDTO;
import org.jana.urlshortnersb.dtos.UrlMappingDTO;
import org.jana.urlshortnersb.models.ClickEvent;
import org.jana.urlshortnersb.models.UrlMapping;
import org.jana.urlshortnersb.models.User;
import org.jana.urlshortnersb.dtos.UpdateUrlRequest;
import org.jana.urlshortnersb.exceptions.BlockedUrlException;
import org.jana.urlshortnersb.repository.ClickEventRepository;
import org.jana.urlshortnersb.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {

    private final UrlSafetyService urlSafetyService;
    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;
    
    public UrlMappingDTO createShortUrl(String originalUrl, User user) {
        // Check URL safety with detailed reason
        String blockReason = urlSafetyService.checkUrlSafety(originalUrl);
        if (blockReason != null) {
            throw new BlockedUrlException("Unsafe URL detected: " + originalUrl, blockReason);
        }
        
        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDto(savedUrlMapping);
    }

    private UrlMappingDTO convertToDto(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDTO;
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));

    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            // Check URL safety before redirecting
            String blockReason = urlSafetyService.checkUrlSafety(urlMapping.getOriginalUrl());
            if (blockReason != null) {
                throw new BlockedUrlException("Unsafe URL detected: " + urlMapping.getOriginalUrl(), blockReason);
            }
            
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            urlMappingRepository.save(urlMapping);

            // Record Click Event
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepository.save(clickEvent);
        }

        return urlMapping;
    }

    public void deleteUrl(Long id, User user) {
        UrlMapping urlMapping = urlMappingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("URL not found"));
        
        // Verify ownership
        if (!urlMapping.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not the owner of this URL");
        }
        
        // Remove all click events explicitly (optional, for clarity)
        clickEventRepository.deleteAll(urlMapping.getClickEvents());
        urlMappingRepository.delete(urlMapping);
    }

    public void updateUrl(Long id, UpdateUrlRequest updateRequest, User user) {
        UrlMapping urlMapping = urlMappingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("URL not found"));
        
        // Verify ownership
        if (!urlMapping.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not the owner of this URL");
        }
        
        // Update URL if provided
        if (updateRequest.getNewURL() != null && !updateRequest.getNewURL().isEmpty()) {
            // Basic URL validation
            if (!updateRequest.getNewURL().startsWith("http")) {
                throw new RuntimeException("Invalid URL format");
            }
            urlMapping.setOriginalUrl(updateRequest.getNewURL());
        }
        
        // Update custom slug if provided
        if (updateRequest.getCustomSlug() != null && !updateRequest.getCustomSlug().isEmpty()) {
            // Check slug uniqueness
            if (urlMappingRepository.findByShortUrl(updateRequest.getCustomSlug()) != null) {
                throw new RuntimeException("Custom slug already exists");
            }
            urlMapping.setShortUrl(updateRequest.getCustomSlug());
        }
        
        urlMappingRepository.save(urlMapping);
    }
}
