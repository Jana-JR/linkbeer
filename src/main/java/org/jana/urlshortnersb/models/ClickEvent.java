package org.jana.urlshortnersb.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime clickDate;
    @ManyToOne
    @JoinColumn(name = "url_mapping_url")
    private UrlMapping urlMapping;

    public ClickEvent() {
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getClickDate() {
        return this.clickDate;
    }

    public UrlMapping getUrlMapping() {
        return this.urlMapping;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClickDate(LocalDateTime clickDate) {
        this.clickDate = clickDate;
    }

    public void setUrlMapping(UrlMapping urlMapping) {
        this.urlMapping = urlMapping;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ClickEvent)) return false;
        final ClickEvent other = (ClickEvent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$clickDate = this.getClickDate();
        final Object other$clickDate = other.getClickDate();
        if (this$clickDate == null ? other$clickDate != null : !this$clickDate.equals(other$clickDate)) return false;
        final Object this$urlMapping = this.getUrlMapping();
        final Object other$urlMapping = other.getUrlMapping();
        if (this$urlMapping == null ? other$urlMapping != null : !this$urlMapping.equals(other$urlMapping))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ClickEvent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $clickDate = this.getClickDate();
        result = result * PRIME + ($clickDate == null ? 43 : $clickDate.hashCode());
        final Object $urlMapping = this.getUrlMapping();
        result = result * PRIME + ($urlMapping == null ? 43 : $urlMapping.hashCode());
        return result;
    }

    public String toString() {
        return "ClickEvent(id=" + this.getId() + ", clickDate=" + this.getClickDate() + ", urlMapping=" + this.getUrlMapping() + ")";
    }
}
