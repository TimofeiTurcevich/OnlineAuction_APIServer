package com.auction.auction_rs.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.web.PageableDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name= "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserImage userImage;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    @ToString.Exclude
    private List<Lot> lots;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lot_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<Lot> favorites = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "lot_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lot_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<Lot> lotsHistory;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private List<Chat> chats;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    @ToString.Exclude
    private List<Message> message;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private List<Bet> bets;


    public void removeFromFavorite(Lot lot){
        favorites.remove(lot);
        lot.getUsersFavorite().remove(this);
    }

    public void addToFavorite(Lot lot){
        favorites.add(lot);
        lot.getUsersFavorite().add(this);
    }

    public void addToHistory(Lot lot){
        lotsHistory.add(lot);
        lot.getUsersLotsHistory().add(this);
    }

    public void removeFromHistory(Lot lot){
        lotsHistory.remove(lot);
        lot.getUsersLotsHistory().remove(this);
    }

    public void addToAuthority(Authority authority){
        authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void removeAuthority(Authority authority){
        authorities.remove(authority);
        authority.getUsers().remove(this);
    }
}
