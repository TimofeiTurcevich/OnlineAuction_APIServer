package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.SubTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubTagRepository extends JpaRepository<SubTag, Long> {

    Optional<SubTag> getAllById(Long id);

    Set<SubTag> getAllBy();
}
