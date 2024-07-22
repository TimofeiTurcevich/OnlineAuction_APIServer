package com.auction.auction_rs.exceptions;

public class EntityNotFoundException extends  NullPointerException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
