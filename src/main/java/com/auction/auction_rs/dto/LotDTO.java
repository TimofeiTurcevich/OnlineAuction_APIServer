package com.auction.auction_rs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class LotDTO {
    private Long id;

    private String title;

    private String endDate;

    private Integer currentBet;

    private Integer startPrice;

    private String coverPhoto;

}
