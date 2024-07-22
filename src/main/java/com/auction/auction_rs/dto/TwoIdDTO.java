package com.auction.auction_rs.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TwoIdDTO {
    private Long firstId;
    private Long secondId;
}
