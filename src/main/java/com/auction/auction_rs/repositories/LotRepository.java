package com.auction.auction_rs.repositories;

import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LotRepository extends JpaRepository<Lot, Long> {

    List<Lot> getAllBy();


    Page<Lot> getAllByTitleLikeOrderByEndDateAsc(String title, Pageable pageable);

    Page<Lot> getAllByTitleLikeOrderByEndDateDesc(String title, Pageable pageable);

    @Query(
            value = """
                    SELECT l FROM Lot l
                    WHERE l.title LIKE :title
                    ORDER BY(
                        if(
                            (SELECT count(*) FROM Bet b WHERE b.lot = l)=0,
                            (l.startPrice),
                            (SELECT max(b.bet) FROM Bet b WHERE b.lot =l)
                        )
                     )DESC
                    """,
            countQuery = """
                         SELECT count(l) FROM Lot l
                         WHERE l.title LIKE :title
                         """
    )
    Page<Lot> getAllByTitleLikeSortedByMaxBetOrStartPriceDesc(String title, Pageable pageable);

    @Query(
            value = """
                    SELECT l FROM Lot l
                    WHERE l.title LIKE :title
                    ORDER BY(
                        if(
                            (SELECT count(*) FROM Bet b WHERE b.lot = l)=0,
                            (l.startPrice),
                            (SELECT max(b.bet) FROM Bet b WHERE b.lot =l)
                        )
                     )ASC
                    """,
            countQuery = """
                         SELECT count(l) FROM Lot l
                         WHERE l.title LIKE :title
                         """
    )
    Page<Lot> getAllByTitleLikeSortedByMaxBetOrStartPriceAsc(String title, Pageable pageable);

    Optional<Lot> getLotById(Long id);

    List<Lot> getAllByTagsIn(Set<SubTag> tags);

    Page<Lot> getAllByTitleLikeAndTagsInOrderByEndDateAsc(String title, Set<SubTag> tags, Pageable pageable);

    Page<Lot> getAllByTitleLikeAndTagsInOrderByEndDateDesc(String title, Set<SubTag> tags, Pageable pageable);

    @Query(
            value = """
                SELECT DISTINCT l
                FROM Lot l
                JOIN l.tags t
                WHERE t IN :tags AND l.title LIKE :title
                    ORDER BY(
                        if(
                            (SELECT count(*) FROM Bet b WHERE b.lot = l)=0,
                            (l.startPrice),
                            (SELECT max(b.bet) FROM Bet b WHERE b.lot =l)
                        )
                     )DESC
                """,
            countQuery = """
                         SELECT count(l) FROM Lot l
                         WHERE l.title LIKE :title
                         """
    )
    Page<Lot> getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceDesc(String title, Set<SubTag> tags, Pageable pageable);

    @Query(
            value = """
                SELECT DISTINCT l
                FROM Lot l
                JOIN l.tags t
                WHERE t IN :tags AND l.title LIKE :title
                    ORDER BY(
                        if(
                            (SELECT count(*) FROM Bet b WHERE b.lot = l)=0,
                            (l.startPrice),
                            (SELECT max(b.bet) FROM Bet b WHERE b.lot =l)
                        )
                     )ASC
                """,
            countQuery = """
                         SELECT count(l) FROM Lot l
                         WHERE l.title LIKE :title
                         """
    )
    Page<Lot> getAllByTitleLikeAndTagsInSortedByMaxBetOrStartPriceAsc(String title, Set<SubTag> tags, Pageable pageable);
}
