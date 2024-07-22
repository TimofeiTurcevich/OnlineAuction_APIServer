package com.auction.auction_rs.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private String message;
    private String sendDate;
    private Long senderId;
    private Long chatId;
}
