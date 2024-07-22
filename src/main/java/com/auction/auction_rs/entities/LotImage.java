package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lot_image")
public class LotImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "lot_id")
    private Lot lot;
}
