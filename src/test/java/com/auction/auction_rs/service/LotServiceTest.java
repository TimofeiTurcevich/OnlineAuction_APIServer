package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.*;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.LotRepository;
import com.auction.auction_rs.repositories.SubTagRepository;
import com.auction.auction_rs.repositories.TagRepository;
import com.auction.auction_rs.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LotServiceTest {

    private final LotRepository lotRepository = mock(LotRepository.class);

    private final SubTagRepository subTagRepository = mock(SubTagRepository.class);

    private final TagRepository tagRepository = mock(TagRepository.class);

    private final UserRepository userRepository = mock(UserRepository.class);

    private final LotService lotService = new LotService(lotRepository,subTagRepository,tagRepository, userRepository);

    private final Pageable pageable = mock(Pageable.class);

    private final List<Lot> lotResult = new ArrayList<>(List.of(
            new Lot(1L, "Some title", "2024-02-02 00:00:00", 100, "Some desc", new ArrayList<>(), null, null, new ArrayList<>(), null, new HashSet<>(), new HashSet<>()),
            new Lot(2L, "Some title2", "2024-02-02 00:00:00", 100, "Some desc2", new ArrayList<>(), null, null, new ArrayList<>(), null, new HashSet<>(), new HashSet<>())
            ));

    private final PageImpl<Lot> page = new PageImpl<>(lotResult, pageable, lotResult.size());

    private final User user = new User(
            1L,
            "Some nickname",
            "Some first name",
            "Some last name",
            "2024-02-02",
            "2024-02-02",
            "Some email",
            "Some password",
            new ArrayList<>(),
            new UserImage(1L,"Some filename", "Some filepath",null),
            lotResult,
            lotResult,
            lotResult,
            null,
            null,
            new ArrayList<>()
    );

    @Test
    void getAllLotsDateAsc(){
        when(lotRepository.getAllByTitleLikeOrderByEndDateAsc("%%", pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getAllLots("dateAsc", "", pageable));
    }
    @Test
    void getAllLotsDateDesc(){
        when(lotRepository.getAllByTitleLikeOrderByEndDateDesc("%%", pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getAllLots("dateDesc", "", pageable));
    }
    @Test
    void getAllLotsPriceAsc(){
        when(lotRepository.getAllByTitleLikeSortedByMaxBetOrStartPriceDesc("%%", pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getAllLots("priceDesc", "", pageable));
    }
    @Test
    void getAllLotsPriceDesc(){
        when(lotRepository.getAllByTitleLikeSortedByMaxBetOrStartPriceAsc("%%", pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getAllLots("priceAsc", "", pageable));
    }

    @Test
    void getLotById(){
        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(lotResult.get(0)));

        assertEquals(lotResult.get(0),lotService.getLotById(1L));
    }

    @Test
    void getLotByWrongId(){
        when(lotRepository.getLotById(2L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->lotService.getLotById(2L));
    }

    @Test
    void getLotsContainingTagDateAsc(){
        Tag temptTag = mock(Tag.class);
        Set<SubTag> subTags = mock(HashSet.class);
        when(tagRepository.getTagById(1L))
                .thenReturn(Optional.of(temptTag));

        when(temptTag.getSubTags())
                .thenReturn(subTags);

        when(lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateAsc("%%", subTags, pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getLotsContainingTag(1L, "dateAsc", "%%", pageable));
    }

    @Test
    void getLotsContainingTagDateDesc(){
        Tag temptTag = mock(Tag.class);
        Set<SubTag> subTags = mock(HashSet.class);
        when(tagRepository.getTagById(1L))
                .thenReturn(Optional.of(temptTag));

        when(temptTag.getSubTags())
                .thenReturn(subTags);

        when(lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateDesc("%%", subTags, pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getLotsContainingTag(1L, "dateDesc", "%%", pageable));
    }
    @Test
    void getLotsContainingTagPriceAsc(){
        Tag temptTag = mock(Tag.class);
        Set<SubTag> subTags = mock(HashSet.class);
        when(tagRepository.getTagById(1L))
                .thenReturn(Optional.of(temptTag));

        when(temptTag.getSubTags())
                .thenReturn(subTags);

        when(lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceDesc("%%", subTags, pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getLotsContainingTag(1L, "priceDesc", "%%", pageable));
    }

    @Test
    void getLotsContainingTagPriceDesc(){
        Tag temptTag = mock(Tag.class);
        Set<SubTag> subTags = mock(HashSet.class);
        when(tagRepository.getTagById(1L))
                .thenReturn(Optional.of(temptTag));

        when(temptTag.getSubTags())
                .thenReturn(subTags);

        when(lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceAsc("%%", subTags, pageable))
                .thenReturn(page);

        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getLotsContainingTag(1L, "priceAsc", "%%", pageable));
    }

    @Test
    void getFavoriteLotsDateAsc(){

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getFavoriteLots(1L, "dateAsc", "", pageable));
    }

    @Test
    void getFavoriteLotsDateDesc(){

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getFavoriteLots(1L, "dateDesc", "", pageable));
    }

    @Test
    void getFavoriteLotsPriceAsc(){

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getFavoriteLots(1L, "priceDesc", "", pageable));
    }

    @Test
    void getFavoriteLotsPriceDesc(){


        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getFavoriteLots(1L, "priceAsc", "", pageable));
    }

    @Test
    void checkIfInFavorite(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(lotResult.get(0)));

        assertEquals(true, lotService.checkIfInFavorite(1L, 1L));
    }

//    @Test
//    void getLotsSubContainingTagDateAsc(){
//        SubTag temptSubTag = new SubTag(
//                1L, "Some title", "Some file name", null, null
//        );
//
//        when(subTagRepository.getAllById(1L))
//                .thenReturn(Optional.of(temptSubTag));
//
//        when(lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateAsc("%%", Set.of(temptSubTag), pageable))
//                .thenReturn(page);
//
//        assertEquals(lotService.getLotDTOS(page,pageable), lotService.getLotsContainingSubTag(1L, "dateAsc", "%%", pageable));
//    }

    @Test
    void addToFavorite(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(lotResult.get(0)));

        when(userRepository.save(user))
                .thenReturn(user);

        assertEquals(true, lotService.addToFavorite(1L, 1L));
    }

    @Test
    void removeFromFavorites(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(lotResult.get(0)));

        when(userRepository.save(user))
                .thenReturn(user);

        assertEquals(false, lotService.removeFromFavorite(1L, 1L));
    }

    @Test
    void getLotsHistoryDateAsc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getLotsHistory(1L, "dateAsc", "", pageable));
    }
    @Test
    void getLotsHistoryDateDesc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getLotsHistory(1L, "dateDesc", "", pageable));
    }
    @Test
    void getLotsHistoryPriceAsc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getLotsHistory(1L, "priceDesc", "", pageable));
    }
    @Test
    void getLotsHistoryPriceDesc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getLotsHistory(1L, "priceAsc", "", pageable));
    }

    @Test
    void getUserLotsDateAsc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getUserLots(1L, "dateAsc", "", pageable));
    }
    @Test
    void getUserLotsDateDesc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getUserLots(1L, "dateDesc", "", pageable));
    }
    @Test
    void getUserLotsPriceAsc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getUserLots(1L, "priceDesc", "", pageable));
    }
    @Test
    void getUserLotsPriceDesc(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotService.getLotsDTOPagination("dateAsc", pageable, user.getFavorites()), lotService.getUserLots(1L, "priceAsc", "", pageable));
    }

    @Test
    void placeABet(){
        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(lotResult.get(0)));

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        assertEquals(lotResult.get(0), lotService.placeABet(1L, 1L, 100));
    }

    @Test
    void createLot(){
        assertEquals(1L, lotService.createLot(lotResult.get(0), new ArrayList<>()));
    }

    @Test
    void deleteLot(){
        assertEquals(true, lotService.deleteLot(1L));
    }
}
