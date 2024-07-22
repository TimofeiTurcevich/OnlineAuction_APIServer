package com.auction.auction_rs.dto;

import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.Message;
import com.auction.auction_rs.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class LoginDTO {
    private String login;
    private String password;
}
