package com.webapplication.service.auctionitem;

import com.webapplication.dto.auctionitem.AddAuctionItemRequestDto;
import com.webapplication.dto.auctionitem.AddAuctionItemResponseDto;
import com.webapplication.dto.auctionitem.AuctionItemBidResponseDto;
import com.webapplication.dto.auctionitem.AuctionItemResponseDto;
import com.webapplication.dto.auctionitem.AuctionItemUpdateRequestDto;
import com.webapplication.dto.auctionitem.StartAuctionDto;
import com.webapplication.dto.auctionitem.AuctionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AuctionItemServiceApi {

    AddAuctionItemResponseDto addAuctionItem(AddAuctionItemRequestDto auctionItemRequestDto) throws Exception;

    List<AuctionItemResponseDto> getAuctionItemsOfUserByStatus(String userId, AuctionStatus status) throws Exception;

    void exportAuctionsAsXmlFile(HttpServletResponse response) throws Exception;

    AuctionItemResponseDto getAuctionItemById(String auctionItemId) throws Exception;

    AuctionItemResponseDto startAuction(String auctionItemId, StartAuctionDto startAuctionDto) throws Exception;

    AuctionItemResponseDto updateAuctionItem(String auctionItemId, AuctionItemUpdateRequestDto auctionItemUpdateRequestDto) throws Exception;

    List<AuctionItemResponseDto> getActiveAuctionItems(Integer from, Integer to) throws Exception;

    String uploadPhoto(MultipartFile file, String userId) throws Exception;

    AuctionItemBidResponseDto bidAuctionItem(String auctionItemId, String userId) throws Exception;

}
