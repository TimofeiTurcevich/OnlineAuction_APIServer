package com.auction.auction_rs.dto;

import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.Message;
import com.auction.auction_rs.entities.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Setter
@Getter
public class ChatDTO {
    private Long id;
    private User user;
    private Lot lot;
    private Message message;
    private String fileName;
}
