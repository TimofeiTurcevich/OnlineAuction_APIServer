package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sub_tag")
@Setter
@Getter
public class SubTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @ManyToMany(mappedBy = "tags")
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    @ToString.Exclude
    private List<Lot> lots;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
