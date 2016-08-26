package com.webapplication.exception;


import com.webapplication.error.auctionitem.AuctionItemError;

public class BidException extends Exception {

    public BidException(AuctionItemError error) {
        super(error.getDescription());
    }

}
