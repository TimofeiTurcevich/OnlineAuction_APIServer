package com.auction.auction_rs.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetDTO {
    private Integer bet;
    private Long userId;
    private Long lotId;
}
