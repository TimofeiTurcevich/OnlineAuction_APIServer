package com.auction.auction_rs.service;

import com.auction.auction_rs.dto.LotDTO;
import com.auction.auction_rs.entities.*;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.LotRepository;
import com.auction.auction_rs.repositories.SubTagRepository;
import com.auction.auction_rs.repositories.TagRepository;
import com.auction.auction_rs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotService {
    private final LotRepository lotRepository;
    private final SubTagRepository subTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    private final String FOLDER_PATH = "D:\\Users\\Timofei\\Desktop\\auction\\";

    public Page<LotDTO> getAllLots(String sortBy, String title, Pageable pageable) {


        title = "%" + title.replaceAll(" ", "%") + "%";

        Page<Lot> allLots = switch (sortBy) {
            case "dateAsc" -> lotRepository.getAllByTitleLikeOrderByEndDateAsc(title, pageable);
            case "dateDesc" -> lotRepository.getAllByTitleLikeOrderByEndDateDesc(title, pageable);
            case "priceDesc" -> lotRepository.getAllByTitleLikeSortedByMaxBetOrStartPriceDesc(title, pageable);
            default -> lotRepository.getAllByTitleLikeSortedByMaxBetOrStartPriceAsc(title, pageable);
        };

        return getLotDTOS(allLots, pageable);

    }

    public Lot getLotById(Long id) {
        return lotRepository.getLotById(id)
                .orElseThrow(()-> new EntityNotFoundException("Lot with such id(" + id + ") wasnt found"));
    }

    public Page<LotDTO> getLotsContainingSubTag(Long id, String sortBy, String title, Pageable pageable) {
        SubTag tempt = subTagRepository.getAllById(id)
                .orElseThrow(()-> new EntityNotFoundException("SubTag with such id(" + id + ") wasnt found"));

        title = "%" + title.replaceAll(" ", "%") + "%";

        Page<Lot> allLots = switch (sortBy) {
            case "dateAsc" -> lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateAsc(title, Set.of(tempt), pageable);
            case "dateDesc" ->
                    lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateDesc(title, Set.of(tempt), pageable);
            case "priceDesc" ->
                    lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceDesc(title, Set.of(tempt), pageable);
            default ->
                    lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceAsc(title, Set.of(tempt), pageable);
        };

        System.out.println(allLots);

        return getLotDTOS(allLots, pageable);
    }

    public Page<LotDTO> getLotsContainingTag(Long id, String sortBy, String title, Pageable pageable) {
        Tag tempt = tagRepository.getTagById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag with such id(" + id + ") wasnt found"));

        Page<Lot> allLots = switch (sortBy) {
            case "dateAsc" ->
                    lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateAsc(title, tempt.getSubTags(), pageable);
            case "dateDesc" ->
                    lotRepository.getAllByTitleLikeAndTagsInOrderByEndDateDesc(title, tempt.getSubTags(), pageable);
            case "priceDesc" ->
                    lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceDesc(title, tempt.getSubTags(), pageable);
            default ->
                    lotRepository.getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceAsc(title, tempt.getSubTags(), pageable);
        };

        return getLotDTOS(allLots, pageable);

    }

    public Page<LotDTO> getFavoriteLots(Long id, String sortBy, String title, Pageable pageable) {
        User tempt = userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + id + ") wasnt found"));
        List<Lot> temptFavorites = tempt.getFavorites()
                .stream().filter(i -> i.getTitle().matches(".*" + title.replaceAll(" ", ".*") + ".*"))
                .toList();

        return getLotsDTOPagination(sortBy, pageable, temptFavorites);
    }

    public Boolean checkIfInFavorite(Long userId, Long lotId) {
        User tempt = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + userId + ") wasnt found"));

        Lot temptLot = lotRepository.getLotById(lotId)
                .orElseThrow(() -> new EntityNotFoundException("Lot with such id(" + lotId + ") wasnt found"));

        return tempt.getFavorites().contains(temptLot);
    }

    @Transactional
    public Boolean addToFavorite(Long userId, Long lotId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + userId + ") wasnt found"));
        Lot tempt = lotRepository.getLotById(lotId)
                .orElseThrow(()-> new EntityNotFoundException("Lot with such id(" + lotId + ") wasnt found"));;
        user.addToFavorite(tempt);
        return userRepository.save(user).getFavorites().contains(tempt);
    }

    @Transactional
    public Boolean removeFromFavorite(Long userId, Long lotId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + userId + ") wasnt found"));
        Lot tempt = lotRepository.getLotById(lotId)
                .orElseThrow(()-> new EntityNotFoundException("Lot with such id(" + lotId + ") wasnt found"));;
        user.removeFromFavorite(tempt);
        return user.getFavorites().contains(tempt);
    }

    public Page<LotDTO> getLotsHistory(Long id, String sortBy, String title, Pageable pageable) {
        User tempt = userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + id + ") wasnt found"));

        List<Lot> temptLotHistory = tempt.getLotsHistory()
                .stream().filter(i -> i.getTitle().matches(".*" + title.replaceAll(" ", ".*") + ".*"))
                .toList();

        return getLotsDTOPagination(sortBy, pageable, temptLotHistory);
    }


    public Page<LotDTO> getUserLots(Long id, String sortBy, String title, Pageable pageable) {
        User tempt = userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + id + ") wasnt found"));

        List<Lot> temptUserLots = tempt.getLots()
                .stream().filter(i -> i.getTitle().matches(".*" + title.replaceAll(" ", ".*") + ".*"))
                .toList();


        return getLotsDTOPagination(sortBy, pageable, temptUserLots);
    }


    @Transactional
    public Lot placeABet(Long userId, Long lotId, Integer bet) {
        Lot temptLot = lotRepository.getLotById(lotId)
                .orElseThrow(()-> new EntityNotFoundException("Lot with such id(" + lotId + ") wasnt found"));;
        User temptUser = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + userId + ") wasnt found"));
        temptLot.getBets().add(
                Bet.builder()
                        .bet(bet)
                        .lot(temptLot)
                        .user(temptUser)
                        .build()
        );
        if(!temptUser.getLotsHistory().contains(temptLot)){
            temptUser.addToHistory(temptLot);
        }
        return temptLot;
    }

    @Transactional
    public Long createLot(Lot lot, List<MultipartFile> files) {
        lotRepository.save(lot);
        files.forEach(i->{
            String filePath = FOLDER_PATH+i.getOriginalFilename();

            lot.getLotImages().add(
                    LotImage.builder()
                            .fileName(i.getOriginalFilename())
                            .filePath(filePath)
                            .lot(lot)
                            .build()
            );

            try {
                i.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return lot.getId();
    }

    public Boolean deleteLot(Long lotId){
        lotRepository.deleteById(lotId);
        return true;
    }

    public Page<LotDTO> getLotDTOS(Page<Lot> lots, Pageable pageable) {
        List<LotDTO> lotDTOS = new ArrayList<>();
        lots.forEach(i -> {
            lotDTOS.add(
                    LotDTO.builder()
                            .title(i.getTitle())
                            .endDate(i.getEndDate())
                            .id(i.getId())
                            .currentBet(i.getBets().size() > 0 ? i.getBets().get(i.getBets().size() - 1).getBet() : null)
                            .startPrice(i.getStartPrice())
                            .coverPhoto(i.getLotImages().size()>0? i.getLotImages().get(0).getFileName() : null)
                            .build()
            );
        });

        return new PageImpl<>(lotDTOS, pageable, lots.getTotalElements());
    }

    private void sortLotDTOS(String sortBy, List<LotDTO> lotDTOS) {
        lotDTOS.sort((i, j) -> {
            switch (sortBy) {
                case "dateAsc" -> {
                    return Timestamp.valueOf(i.getEndDate()).compareTo(Timestamp.valueOf(j.getEndDate()));
                }
                    case "dateDesc" -> {
                    return Timestamp.valueOf(j.getEndDate()).compareTo(Timestamp.valueOf(i.getEndDate()));
                }
                case "priceDesc" -> {
                    return j.getCurrentBet() != null
                            ?
                            j.getCurrentBet().compareTo(i.getCurrentBet() != null ? i.getCurrentBet() : i.getStartPrice())
                            :
                            j.getStartPrice().compareTo(i.getCurrentBet() != null ? i.getCurrentBet() : i.getStartPrice());
                }
                default -> {
                    return i.getCurrentBet() != null
                            ?
                            i.getCurrentBet().compareTo(j.getCurrentBet() != null ? j.getCurrentBet() : j.getStartPrice())
                            :
                            i.getStartPrice().compareTo(j.getCurrentBet() != null ? j.getCurrentBet() : j.getStartPrice());
                }
            }
        });
    }

    public Page<LotDTO> getLotsDTOPagination(String sortBy, Pageable pageable, List<Lot> lots) {
        List<LotDTO> lotDTOS = new ArrayList<>();
        if (lots.size() > 0 && ((float) lots.size() / 4) > pageable.getPageNumber()) {
            for (int i = pageable.getPageNumber() * 4;
                 i < (
                         lots.size() / (lots.size() / ((pageable.getPageNumber() + 1.0) * 4.0))
                                 -
                                 ((pageable.getPageNumber() + 1) * 4 <= lots.size() ?
                                         0
                                         :
                                         (4 - (4 * (lots.size() % 4) / 4.0)))
                 );
                 i++) {

                lotDTOS.add(LotDTO.builder()
                        .title(lots.get(i).getTitle())
                        .endDate(lots.get(i).getEndDate())
                        .id(lots.get(i).getId())
                        .currentBet(lots.get(i).getBets().size() > 0 ?
                                lots.get(i)
                                        .getBets().get(
                                                lots.get(i).getBets().size() - 1
                                        ).getBet()
                                : null)
                        .startPrice(lots.get(i).getStartPrice())
                        .coverPhoto(lots.get(i).getLotImages().size()>0? lots.get(i).getLotImages().get(0).getFileName() : null)
                        .build());
            }
        }

        sortLotDTOS(sortBy, lotDTOS);

        return new PageImpl<>(lotDTOS, pageable, lots.size());
    }
}
