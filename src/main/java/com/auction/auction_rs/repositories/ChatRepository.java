package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.Chat;
import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> getAllByUserOrLot_Seller(User user, User lot_seller);

    Optional<Chat> getAllById(Long id);

    Optional<Chat> getAllByUserAndLot(User user, Lot lot);
}
