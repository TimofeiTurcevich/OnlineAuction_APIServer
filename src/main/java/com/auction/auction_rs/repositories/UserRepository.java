package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.nickname = :nickname")
    User getUserByNickname(String nickname);
}
