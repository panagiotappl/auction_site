package com.webapplication.validator.ValidatorWrapper;


import com.webapplication.dto.auctionitem.AddAuctionItemRequestDto;
import com.webapplication.dto.auctionitem.AuctionItemUpdateRequestDto;
import com.webapplication.dto.auctionitem.BidRequestDto;
import com.webapplication.dto.auctionitem.SearchAuctionItemDto;
import com.webapplication.dto.auctionitem.StartAuctionDto;
import com.webapplication.exception.ValidationException;

public interface AuctionItemRequestValidatorWrapper {

    void validate(AddAuctionItemRequestDto addAuctionItemRequestDto) throws ValidationException;

    void validate(StartAuctionDto startAuctionDto) throws ValidationException;

    void validate(AuctionItemUpdateRequestDto auctionItemUpdateRequestDto) throws ValidationException;

    void validate(BidRequestDto bidRequestDto) throws ValidationException;

    void validate(SearchAuctionItemDto searchAuctionItemDto) throws ValidationException;

}
