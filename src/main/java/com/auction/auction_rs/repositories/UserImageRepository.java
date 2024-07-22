package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.LotImage;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.entities.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    UserImage getAllByFileName(String fileName);
}
