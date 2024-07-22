package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "user_image")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @OneToOne
    @MapsId
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;
}
