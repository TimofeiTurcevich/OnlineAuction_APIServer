package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "lot")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "start_price")
    private Integer startPrice;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<LotImage> lotImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "lot_tags",
            joinColumns = @JoinColumn(name = "lot_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_tag_id")
    )
    private Set<SubTag> tags;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Bet> bets;

    @OneToMany(mappedBy = "lot")
    @JsonIgnore
    @ToString.Exclude
    private List<Chat> chats;

    @ManyToMany(mappedBy = "favorites")
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    @ToString.Exclude
    private Set<User> usersFavorite;

    @ManyToMany(mappedBy = "lotsHistory")
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    @ToString.Exclude
    private Set<User> usersLotsHistory;

}
