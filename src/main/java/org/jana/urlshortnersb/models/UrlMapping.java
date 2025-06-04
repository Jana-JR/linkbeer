package org.jana.urlshortnersb.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Integer clickCount = 0;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "urlMapping")
    private List<ClickEvent> clickEvents;

    public UrlMapping() {
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

    public Integer getClickCount() {
        return this.clickCount;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public User getUser() {
        return this.user;
    }

    public List<ClickEvent> getClickEvents() {
        return this.clickEvents;
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

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClickEvents(List<ClickEvent> clickEvents) {
        this.clickEvents = clickEvents;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UrlMapping)) return false;
        final UrlMapping other = (UrlMapping) o;
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
        final Object this$clickCount = this.getClickCount();
        final Object other$clickCount = other.getClickCount();
        if (this$clickCount == null ? other$clickCount != null : !this$clickCount.equals(other$clickCount))
            return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$clickEvents = this.getClickEvents();
        final Object other$clickEvents = other.getClickEvents();
        if (this$clickEvents == null ? other$clickEvents != null : !this$clickEvents.equals(other$clickEvents))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UrlMapping;
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
        final Object $clickCount = this.getClickCount();
        result = result * PRIME + ($clickCount == null ? 43 : $clickCount.hashCode());
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $clickEvents = this.getClickEvents();
        result = result * PRIME + ($clickEvents == null ? 43 : $clickEvents.hashCode());
        return result;
    }

    public String toString() {
        return "UrlMapping(id=" + this.getId() + ", originalUrl=" + this.getOriginalUrl() + ", shortUrl=" + this.getShortUrl() + ", clickCount=" + this.getClickCount() + ", createdDate=" + this.getCreatedDate() + ", user=" + this.getUser() + ", clickEvents=" + this.getClickEvents() + ")";
    }
}
