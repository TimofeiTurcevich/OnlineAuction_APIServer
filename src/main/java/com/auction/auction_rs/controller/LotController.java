package com.auction.auction_rs.controller;

import com.auction.auction_rs.dto.BetDTO;
import com.auction.auction_rs.dto.LotDTO;
import com.auction.auction_rs.dto.TwoIdDTO;
import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.Tag;
import com.auction.auction_rs.service.LotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;

    @GetMapping
    public Page<LotDTO> getAllLots(@RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        return lotService.getAllLots(sortBy, title, pageable);
    }

    @GetMapping("/byId")
    public ResponseEntity<Lot> getLotById(@RequestParam Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.getLotById(id));
    }

    @GetMapping("/bySubTag")
    public Page<LotDTO> getLotsBySubTag(@RequestParam Long id, @RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        return lotService.getLotsContainingSubTag(id, sortBy, title, pageable);
    }

    @GetMapping("/byTag")
    public Page<LotDTO> getLotsByTag(@RequestParam Long id, @RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        return lotService.getLotsContainingTag(id, sortBy, title, pageable);
    }

    @GetMapping("/favorite")
    public Page<LotDTO> getFavoriteLots(@RequestParam Long id, @RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        return lotService.getFavoriteLots(id, sortBy, title,pageable);
    }

    @GetMapping("/favorite/check")
    public ResponseEntity<Boolean> checkIfInFavorite(@RequestParam Long userId, @RequestParam Long lotId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.checkIfInFavorite(userId, lotId));
    }

    @PostMapping("/addToFavorite")
    public ResponseEntity<Boolean> addToFavorite(@RequestBody TwoIdDTO twoIdDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.addToFavorite(twoIdDTO.getFirstId(), twoIdDTO.getSecondId()));
    }

    @DeleteMapping("/removeFromFavorite")
    public ResponseEntity<Boolean> removeFromFavorite(@RequestParam Long userId, @RequestParam Long lotId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.removeFromFavorite(userId, lotId));
    }

    @GetMapping("/lotsHistory")
    public Page<LotDTO> getLotsHistory(@RequestParam Long id, @RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        return lotService.getLotsHistory(id, sortBy, title, pageable);
    }

    @GetMapping("/userLots")
    public Page<LotDTO> getUserLots(@RequestParam Long id, @RequestParam String sortBy, @RequestParam String title, Pageable pageable) {
        System.out.println(lotService.getUserLots(id, sortBy, title, pageable));
        return lotService.getUserLots(id, sortBy, title, pageable);
    }

    @PostMapping("/placeABet")
    public ResponseEntity<Lot> placeABet(@RequestBody BetDTO betDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.placeABet(betDTO.getUserId(), betDTO.getLotId(), betDTO.getBet()));
    }

    @PostMapping("/createLot")
    public ResponseEntity<Long> createLot(@RequestParam Lot lot, @RequestParam("images") List<MultipartFile> files) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.createLot(lot,files));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteLot(@RequestParam Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lotService.deleteLot(id));
    }
}
