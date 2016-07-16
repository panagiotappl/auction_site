package com.webapplication.dto.auctionitem;


import com.webapplication.dto.category.CategoryResponseDto;
import com.webapplication.dto.user.GeoLocationDto;

import java.util.Date;
import java.util.List;

public class AuctionItemResponseDto {

    private Integer auctionItemId;
    private String name;
    private Double currentBid;
    private Double buyout;
    private Double minBid;
    private Integer bidsNo;
    private String description;
    private Date startDate;
    private Date endDate;
    private GeoLocationDto geoLocationDto;
    private List<CategoryResponseDto> categoryResponseDtoList;
    private Integer userId;

    public Integer getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Integer auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public Double getBuyout() {
        return buyout;
    }

    public void setBuyout(Double buyout) {
        this.buyout = buyout;
    }

    public Double getMinBid() {
        return minBid;
    }

    public void setMinBid(Double minBid) {
        this.minBid = minBid;
    }

    public Integer getBidsNo() {
        return bidsNo;
    }

    public void setBidsNo(Integer bidsNo) {
        this.bidsNo = bidsNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public GeoLocationDto getGeoLocationDto() {
        return geoLocationDto;
    }

    public void setGeoLocationDto(GeoLocationDto geoLocationDto) {
        this.geoLocationDto = geoLocationDto;
    }

    public List<CategoryResponseDto> getCategoryResponseDtoList() {
        return categoryResponseDtoList;
    }

    public void setCategoryResponseDtoList(List<CategoryResponseDto> categoryResponseDtoList) {
        this.categoryResponseDtoList = categoryResponseDtoList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}