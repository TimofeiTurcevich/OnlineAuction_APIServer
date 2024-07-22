package com.auction.auction_rs.config;


import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {
    private final ObjectMapper objectMapper;

    public StringToUserConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public User convert(String source) {
        try {
            return objectMapper.readValue(source, User.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert string to Lot", e);
        }
    }
}
