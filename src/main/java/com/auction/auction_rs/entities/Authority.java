package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
@Table(name = "authority")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authorities")
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    @ToString.Exclude
    private Set<User> users;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
