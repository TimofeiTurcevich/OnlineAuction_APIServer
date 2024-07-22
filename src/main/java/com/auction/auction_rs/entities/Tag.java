package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tag")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "tag")
    @OrderBy("id")
    @JsonIgnore
    @ToString.Exclude
    private Set<SubTag> subTags;
}
