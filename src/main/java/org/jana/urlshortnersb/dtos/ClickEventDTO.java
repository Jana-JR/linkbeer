package org.jana.urlshortnersb.dtos;


import java.time.LocalDate;

public class ClickEventDTO {
    private LocalDate clickDate;
    private Long count;

    public ClickEventDTO() {
    }

    public LocalDate getClickDate() {
        return this.clickDate;
    }

    public Long getCount() {
        return this.count;
    }

    public void setClickDate(LocalDate clickDate) {
        this.clickDate = clickDate;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ClickEventDTO)) return false;
        final ClickEventDTO other = (ClickEventDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$clickDate = this.getClickDate();
        final Object other$clickDate = other.getClickDate();
        if (this$clickDate == null ? other$clickDate != null : !this$clickDate.equals(other$clickDate)) return false;
        final Object this$count = this.getCount();
        final Object other$count = other.getCount();
        if (this$count == null ? other$count != null : !this$count.equals(other$count)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ClickEventDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $clickDate = this.getClickDate();
        result = result * PRIME + ($clickDate == null ? 43 : $clickDate.hashCode());
        final Object $count = this.getCount();
        result = result * PRIME + ($count == null ? 43 : $count.hashCode());
        return result;
    }

    public String toString() {
        return "ClickEventDTO(clickDate=" + this.getClickDate() + ", count=" + this.getCount() + ")";
    }
}
