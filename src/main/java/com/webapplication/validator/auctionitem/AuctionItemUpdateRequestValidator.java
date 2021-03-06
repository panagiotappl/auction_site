package com.webapplication.validator.auctionitem;


import com.google.common.base.Strings;
import com.webapplication.dto.auctionitem.AuctionItemUpdateRequestDto;
import com.webapplication.dto.user.GeoLocationDto;
import com.webapplication.error.auctionitem.AuctionItemError;
import com.webapplication.exception.ValidationException;
import com.webapplication.validator.Validator;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class AuctionItemUpdateRequestValidator implements Validator<AuctionItemUpdateRequestDto> {

    private Range<Double> latitudeRange = new Range<>(-90.0, 90.0);
    private Range<Double> longitudeRange = new Range<>(-180.0, 180.0);

    @Override
    public void validate(AuctionItemUpdateRequestDto request) throws ValidationException {
        Optional.ofNullable(request).orElseThrow(() -> new ValidationException(AuctionItemError.MISSING_DATA));
        if (Stream.of(request.getDescription(), request.getName(), request.getCategoryIds(), request.getMinBid(), request.getCountry(), request.getImages()).anyMatch(Objects::isNull))
            throw new ValidationException(AuctionItemError.MISSING_DATA);

        if (Stream.of(request.getBuyout(), request.getMinBid()).filter(Objects::nonNull).count() == 2)
            if (request.getMinBid() > request.getBuyout())
                throw new ValidationException(AuctionItemError.INVALID_DATA);

        if (Stream.of(request.getMinBid(), request.getBuyout())
                .filter(Objects::nonNull)
                .anyMatch(value -> value <= 0))
            throw new ValidationException(AuctionItemError.INVALID_DATA);

        if (Stream.of(request.getName(), request.getDescription(), request.getCountry()).anyMatch(String::isEmpty))
            throw new ValidationException(AuctionItemError.INVALID_DATA);

        if (request.getCategoryIds().isEmpty())
            throw new ValidationException(AuctionItemError.MISSING_DATA);

        if (request.getCategoryIds().stream().anyMatch(Strings::isNullOrEmpty))
            throw new ValidationException(AuctionItemError.INVALID_DATA);

        GeoLocationDto geoLocationDto = request.getGeoLocationDto();
        if (geoLocationDto != null) {
            Double latitude = geoLocationDto.getLatitude();
            Double longitude = geoLocationDto.getLongitude();
            Optional.ofNullable(latitude).orElseThrow(() -> new ValidationException(AuctionItemError.MISSING_DATA));
            Optional.ofNullable(longitude).orElseThrow(() -> new ValidationException(AuctionItemError.MISSING_DATA));

            if (!latitudeRange.contains(latitude) || !longitudeRange.contains(longitude))
                throw new ValidationException(AuctionItemError.INVALID_DATA);
        }
    }
}
