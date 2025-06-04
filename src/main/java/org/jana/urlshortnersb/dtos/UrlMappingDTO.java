package org.jana.urlshortnersb.dtos;


import java.time.LocalDateTime;

public class UrlMappingDTO {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    private String username;

    public UrlMappingDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public int getClickCount() {
        return this.clickCount;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UrlMappingDTO)) return false;
        final UrlMappingDTO other = (UrlMappingDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$originalUrl = this.getOriginalUrl();
        final Object other$originalUrl = other.getOriginalUrl();
        if (this$originalUrl == null ? other$originalUrl != null : !this$originalUrl.equals(other$originalUrl))
            return false;
        final Object this$shortUrl = this.getShortUrl();
        final Object other$shortUrl = other.getShortUrl();
        if (this$shortUrl == null ? other$shortUrl != null : !this$shortUrl.equals(other$shortUrl)) return false;
        if (this.getClickCount() != other.getClickCount()) return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UrlMappingDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $originalUrl = this.getOriginalUrl();
        result = result * PRIME + ($originalUrl == null ? 43 : $originalUrl.hashCode());
        final Object $shortUrl = this.getShortUrl();
        result = result * PRIME + ($shortUrl == null ? 43 : $shortUrl.hashCode());
        result = result * PRIME + this.getClickCount();
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        return result;
    }

    public String toString() {
        return "UrlMappingDTO(id=" + this.getId() + ", originalUrl=" + this.getOriginalUrl() + ", shortUrl=" + this.getShortUrl() + ", clickCount=" + this.getClickCount() + ", createdDate=" + this.getCreatedDate() + ", username=" + this.getUsername() + ")";
    }
}
