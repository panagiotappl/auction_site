package com.webapplication.service.auctionitem

import com.webapplication.dao.AuctionItemRepository
import com.webapplication.dao.UserRepository
import com.webapplication.dto.auctionitem.AddAuctionItemRequestDto
import com.webapplication.dto.auctionitem.AddAuctionItemResponseDto
import com.webapplication.dto.auctionitem.AuctionItemResponseDto
import com.webapplication.dto.auctionitem.StartAuctionDto
import com.webapplication.dto.auctionitem.AuctionStatus
import com.webapplication.entity.AuctionItem
import com.webapplication.entity.User
import com.webapplication.error.auctionitem.AuctionItemError
import com.webapplication.exception.auctionitem.AuctionAlreadyInProgressException
import com.webapplication.exception.auctionitem.AuctionDurationTooShortException
import com.webapplication.exception.auctionitem.AuctionItemNotFoundException
import com.webapplication.exception.user.UserNotFoundException
import com.webapplication.mapper.AuctionItemMapper
import org.joda.time.DateTime
import spock.lang.Specification
import spock.lang.Unroll

class AuctionItemServiceApiImplSpec extends Specification {

    AuctionItemRepository mockAuctionItemRepository
    AuctionItemMapper mockAuctionItemMapper
    UserRepository mockUserRepository
    AuctionItemServiceApi auctionItemServiceImpl
    Integer minAuctionDurationInHours

    def setup() {
        mockAuctionItemRepository = Mock(AuctionItemRepository)
        mockAuctionItemMapper = Mock(AuctionItemMapper)
        mockUserRepository = Mock(UserRepository)
        minAuctionDurationInHours = 1

        auctionItemServiceImpl = new AuctionItemServiceApiImpl(auctionItemMongoRepository: mockAuctionItemRepository, auctionItemMapper: mockAuctionItemMapper, userRepository: mockUserRepository, minAuctionDurationInHours: minAuctionDurationInHours)
    }

    def "Add an auction item"() {
        given:
        AddAuctionItemRequestDto addAuctionItemRequestDto = new AddAuctionItemRequestDto()
        AuctionItem auctionItem = new AuctionItem(userId: '578f869f5a61a77b7915252a')
        AddAuctionItemResponseDto addAuctionItemResponseDto = new AddAuctionItemResponseDto()

        when:
        auctionItemServiceImpl.addAuctionItem(addAuctionItemRequestDto)

        then:
        1 * mockAuctionItemMapper.addAuctionItemRequestDtoToAuctionItem(addAuctionItemRequestDto) >> auctionItem
        1 * mockUserRepository.findUserByUserId('578f869f5a61a77b7915252a') >> new User()
        1 * mockAuctionItemRepository.save(auctionItem)
        1 * mockAuctionItemMapper.auctionItemToAddAuctionItemResponseDto(auctionItem) >> addAuctionItemResponseDto
        0 * _
    }

    def "Add an auction item from a user that doesn't exit"() {
        given:
        AddAuctionItemRequestDto addAuctionItemRequestDto = new AddAuctionItemRequestDto()
        AuctionItem auctionItem = new AuctionItem(userId: '578f869f5a61a77b7915252a')
        AddAuctionItemResponseDto addAuctionItemResponseDto = new AddAuctionItemResponseDto()

        when:
        auctionItemServiceImpl.addAuctionItem(addAuctionItemRequestDto)

        then:
        1 * mockAuctionItemMapper.addAuctionItemRequestDtoToAuctionItem(addAuctionItemRequestDto) >> auctionItem
        1 * mockUserRepository.findUserByUserId('578f869f5a61a77b7915252a') >> null
        UserNotFoundException e = thrown()
        e.localizedMessage == AuctionItemError.USER_NOT_FOUND.description
    }

    @Unroll
    def "Get all auctions of a specific user and a status"() {
        given:
        String userId = '578f869f5a61a77b7915252a'
        List<AuctionItem> auctionItemList = []
        List<AuctionItemResponseDto> auctionItemResponseDtoList = []

        when:
        auctionItemServiceImpl.getAuctionItemsOfUserByStatus(userId, status)

        then:
        (status == AuctionStatus.ACTIVE ? 1 : 0) * mockAuctionItemRepository.findActiveAuctionsOfUser(userId, *_) >> auctionItemList
        (status == AuctionStatus.PENDING ? 1 : 0) * mockAuctionItemRepository.findPendingAuctionsOfUser(userId) >> auctionItemList
        (status == AuctionStatus.INACTIVE ? 1 : 0) * mockAuctionItemRepository.findInactiveAuctionsOfUser(userId, *_)  >> auctionItemList
        1 * mockAuctionItemMapper.auctionItemsToAuctionItemResponseDto(auctionItemList) >> auctionItemResponseDtoList

        where:
        status << [AuctionStatus.ACTIVE, AuctionStatus.PENDING, AuctionStatus.INACTIVE]
    }

    def "Get auction item by auctionItemId that doesn't exist"() {
        given:
        String auctionItemId = '578f869f5a61a77b7915252a'

        when:
        auctionItemServiceImpl.getAuctionItemById(auctionItemId)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> null
        AuctionItemNotFoundException e = thrown()
        e.localizedMessage == AuctionItemError.AUCTION_ITEM_NOT_FOUND.description
    }

    def "Get auction item by aucitonItemId"() {
        given:
        String auctionItemId = '578f869f5a61a77b7915252a'
        AuctionItem auctionItem = new AuctionItem()
        AuctionItemResponseDto auctionItemResponseDto = new AuctionItemResponseDto()

        when:
        auctionItemServiceImpl.getAuctionItemById(auctionItemId)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> auctionItem
        1 * mockAuctionItemMapper.auctionItemToAuctionItemResponseDto(auctionItem) >> auctionItemResponseDto
        0 * _
    }

    def "User starts auction with a non existent auction item id"() {
        given:
        String auctionItemId = '578f9c605a61fe0aa84fe8e5'
        StartAuctionDto startAuctionDto = new StartAuctionDto()

        when:
        auctionItemServiceImpl.startAuction(auctionItemId, startAuctionDto)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> null
        AuctionItemNotFoundException e = thrown()
        e.localizedMessage == AuctionItemError.AUCTION_ITEM_NOT_FOUND.description
    }

    def "User starts auction which is already started"() {
        given:
        String auctionItemId = '578f9c605a61fe0aa84fe8e5'
        StartAuctionDto startAuctionDto = new StartAuctionDto()
        AuctionItem auctionItem = new AuctionItem(startDate: new Date())

        when:
        auctionItemServiceImpl.startAuction(auctionItemId, startAuctionDto)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> auctionItem
        AuctionAlreadyInProgressException e = thrown()
        e.localizedMessage == AuctionItemError.AUCTION_ALREADY_IN_PROGRESS.description
    }

    def "User starts auction with duration less than an hour"() {
        given:
        String auctionItemId = '578f9c605a61fe0aa84fe8e5'
        StartAuctionDto startAuctionDto = new StartAuctionDto(endDate: DateTime.now().plusMinutes(55).toDate())
        AuctionItem auctionItem = new AuctionItem(startDate: null)

        when:
        auctionItemServiceImpl.startAuction(auctionItemId, startAuctionDto)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> auctionItem
        AuctionDurationTooShortException e = thrown()
        e.localizedMessage == AuctionItemError.AUCTION_DURATION_TOO_SHORT.description
    }

    def "User starts his auction"() {
        given:
        String auctionItemId = '578f9c605a61fe0aa84fe8e5'
        StartAuctionDto startAuctionDto = new StartAuctionDto(endDate: DateTime.now().plusMinutes(61).toDate())
        AuctionItem auctionItem = new AuctionItem(startDate: null)
        AuctionItemResponseDto auctionItemResponseDto = new AuctionItemResponseDto()

        when:
        auctionItemServiceImpl.startAuction(auctionItemId, startAuctionDto)

        then:
        1 * mockAuctionItemRepository.findAuctionItemByAuctionItemId(auctionItemId) >> auctionItem
        1 * mockAuctionItemMapper.update(auctionItem, _ as Date, startAuctionDto.endDate)
        1 * mockAuctionItemRepository.save(auctionItem) >> auctionItem
        1 * mockAuctionItemMapper.auctionItemToAuctionItemResponseDto(auctionItem) >> auctionItemResponseDto
        0 * _
    }


}
