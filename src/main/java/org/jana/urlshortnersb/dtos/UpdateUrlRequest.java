package org.jana.urlshortnersb.dtos;

public class UpdateUrlRequest {
    private String newURL;
    private String customSlug;

    // Getters and setters
    public String getNewURL() {
        return newURL;
    }

    public void setNewURL(String newURL) {
        this.newURL = newURL;
    }

    public String getCustomSlug() {
        return customSlug;
    }

    public void setCustomSlug(String customSlug) {
        this.customSlug = customSlug;
    }
}
