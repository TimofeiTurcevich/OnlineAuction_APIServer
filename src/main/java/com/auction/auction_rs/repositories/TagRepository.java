package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Set<Tag> getAllBy();

    Optional<Tag> getTagById(Long id);
}
