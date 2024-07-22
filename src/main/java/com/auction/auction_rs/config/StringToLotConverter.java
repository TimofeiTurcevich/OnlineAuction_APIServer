package com.auction.auction_rs.config;

import com.auction.auction_rs.entities.Lot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;

public class StringToLotConverter implements Converter<String, Lot> {

    private final ObjectMapper objectMapper;

    public StringToLotConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Lot convert(String source) {
        try {
            return objectMapper.readValue(source, Lot.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert string to Lot", e);
        }
    }
}