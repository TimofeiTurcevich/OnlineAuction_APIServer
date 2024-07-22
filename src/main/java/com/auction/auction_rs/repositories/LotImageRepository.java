package com.auction.auction_rs.repositories;


import com.auction.auction_rs.entities.LotImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotImageRepository  extends JpaRepository<LotImage, Long> {
    LotImage getAllByFileName(String fileName);
}
