package com.creationline.k8sthinkit.sample1.rank.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.creationline.k8sthinkit.sample1.rank.controller.response.DailyRankResponse;
import com.creationline.k8sthinkit.sample1.rank.controller.response.RankEntry;
import com.creationline.k8sthinkit.sample1.rank.repository.DailyTotalEntryRepository;
import com.creationline.k8sthinkit.sample1.rank.repository.DailyUniqueEntryRepository;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;
import com.creationline.k8sthinkit.sample1.rank.service.ranking.RankCalculationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ranks")
public class RankController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankController.class);

    private final DailyTotalEntryRepository dailyTotalEntryRepository;
    private final DailyUniqueEntryRepository dailyUniqueEntryRepository;
    private final RankCalculationService rankCalculationService;

    @Autowired
    public RankController( //

        @NonNull final DailyTotalEntryRepository dailyTotalEntryRepository, //
        @NonNull final DailyUniqueEntryRepository dailyUniqueEntryRepository, //
        @NonNull final RankCalculationService rankCalculationService //

    ) {

        this.dailyTotalEntryRepository = dailyTotalEntryRepository;
        this.dailyUniqueEntryRepository = dailyUniqueEntryRepository;
        this.rankCalculationService = rankCalculationService;

    }

    @GetMapping( //
        path = { //
            "/daily/", //
            "/daily" //
        }, //
        params = { //
            "date"
        }, //
        produces = { //
            Controllers.MIMETYPE_PRODUCING //
        } //
    )
    public Mono<DailyRankResponse> getDailyRank( //

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
        @RequestParam("date") //
        @NonNull //
        final LocalDate date //

    ) {

        LOGGER.debug("/ dispatched.");
        LOGGER.trace("  date = {}", date);

        final Mono<List<RankEntry>> totalAccessEntries = this.dailyTotalEntryRepository.findByDate(date) //
            .map(this::dailyTotalEntryToRankEntry) //
            .sort(Comparator.comparing(RankEntry::getAccess).reversed()) //
            .collectList();

        final Mono<List<RankEntry>> uniqueAccessEntries = this.dailyUniqueEntryRepository.findByDate(date) //
            .map(this::dailyUniqueEntryToRankEntry) //
            .sort(Comparator.comparing(RankEntry::getAccess).reversed()) //
            .collectList();

        return Mono.zip(totalAccessEntries, uniqueAccessEntries) //
            .map( //
                tuple -> new DailyRankResponse( //
                    date, //
                    tuple.getT1(), //
                    tuple.getT2() //
                ) //
            );

    }

    @PutMapping( //
        path = { //
            "/daily/", //
            "/daily" //
        }, //
        params = {
            "date", //
        } //
    )
    public Mono<ResponseEntity<?>> updateDailyRank( //

        @RequestParam("date") //
        final LocalDate date, //
        @NonNull //
        final UriComponentsBuilder baseUri //

    ) {

        LOGGER.debug("start manual daily rank update for {}", date);

        final Flux<DailyTotalEntry> updatedTotalRanks = this.rankCalculationService.updateDailyTotalAccessRank(date);
        final Flux<DailyUniqueEntry> updatedUniqueRanks = this.rankCalculationService.updateDailyUniqueAccessRank(date);

        Flux.zip(updatedTotalRanks, updatedUniqueRanks).blockLast();

        LOGGER.debug("complete manual daily rank update for {}", date);

        final URI createdURI = baseUri.queryParam("date", date) //
            .build() //
            .toUri();
        return Mono.just( //
            ResponseEntity.created(createdURI) //
                .build() //
        );

    }

    private RankEntry dailyTotalEntryToRankEntry(@NonNull final DailyTotalEntry totalEntry) {
        return new RankEntry( //
            totalEntry.getArticleId(), //
            totalEntry.getTotalAccess() //
        );
    }

    private RankEntry dailyUniqueEntryToRankEntry(@NonNull final DailyUniqueEntry uniqueEntry) {
        return new RankEntry( //
            uniqueEntry.getArticleId(), //
            uniqueEntry.getUniqueAccess() //
        );
    }

}
