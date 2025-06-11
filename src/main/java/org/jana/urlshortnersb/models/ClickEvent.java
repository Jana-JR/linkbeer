package org.jana.urlshortnersb.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime clickDate;
    @ManyToOne
    @JoinColumn(name = "url_mapping_url")
    private UrlMapping urlMapping;


}
