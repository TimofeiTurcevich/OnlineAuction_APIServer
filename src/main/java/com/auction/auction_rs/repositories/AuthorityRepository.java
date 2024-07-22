package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority getAuthoritiesByName(String name);
}
