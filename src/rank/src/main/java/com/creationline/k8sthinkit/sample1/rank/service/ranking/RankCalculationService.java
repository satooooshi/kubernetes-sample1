package com.creationline.k8sthinkit.sample1.rank.service.ranking;

import java.time.LocalDate;
import java.util.Comparator;

import com.creationline.k8sthinkit.sample1.rank.repository.DailyTotalEntryRepository;
import com.creationline.k8sthinkit.sample1.rank.repository.DailyUniqueEntryRepository;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;
import com.creationline.k8sthinkit.sample1.rank.service.accesscount.AccesscountService;
import com.creationline.k8sthinkit.sample1.rank.service.accesscount.entity.AccessStats;
import com.creationline.k8sthinkit.sample1.rank.service.article.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RankCalculationService {

    private final AccesscountService accesscountService;
    private final ArticleService articleService;
    private final DailyTotalEntryRepository dailyTotalEntryRepository;
    private final DailyUniqueEntryRepository dailyUniqueEntryRepository;

    @Autowired
    public RankCalculationService( //
        @NonNull //
        final AccesscountService accesscountService, //
        @NonNull //
        final ArticleService articleService, //
        @NonNull //
        final DailyTotalEntryRepository dailyTotalEntryRepository, //
        @NonNull //
        final DailyUniqueEntryRepository dailyUniqueEntryRepository //
    ) {

        this.accesscountService = accesscountService;
        this.articleService = articleService;
        this.dailyTotalEntryRepository = dailyTotalEntryRepository;
        this.dailyUniqueEntryRepository = dailyUniqueEntryRepository;

    }

    public Flux<DailyUniqueEntry> updateDailyUniqueAccessRank( //

        @NonNull //
        final LocalDate date //

    ) {

        final Flux<DailyUniqueEntry> existingEntries = this.dailyUniqueEntryRepository.findByDate(date) //
            .sort(Comparator.comparing(DailyUniqueEntry::getRank)) //
            .concatWith(Mono.just(new DailyUniqueEntry(null, LocalDate.ofEpochDay(-1L), -1, -1L, -1L)).repeat(10L));

        final Flux<DailyUniqueEntry> entriesToSave = this.articleService.listAllArticle() //
            .flatMap(articleEntry -> this.accesscountService.calculateStats(articleEntry.getId(), date, date.plusDays(1L))) //
            .sort(Comparator.comparing(AccessStats::getUniqueAccess)) //
            .take(10L) //
            .zipWith(Flux.range(1, 10)) //
            .map(tpl -> new DailyUniqueEntry( //
                null, //
                date, //
                tpl.getT2(), //
                tpl.getT1().getArticleId(), //
                tpl.getT1().getUniqueAccess()
            )) //
            .zipWith(existingEntries) //
            .map(tpl -> {
                if (tpl.getT2().getId() == null) {
                    return tpl.getT1();
                }
                return tpl.getT1().withId(tpl.getT2().getId());
            });

        return this.dailyUniqueEntryRepository.saveAll(entriesToSave);

    }

    public Flux<DailyTotalEntry> updateDailyTotalAccessRank( //

        @NonNull //
        final LocalDate date //

    ) {

        final Flux<DailyTotalEntry> existingEntries = this.dailyTotalEntryRepository.findByDate(date) //
            .sort(Comparator.comparing(DailyTotalEntry::getRank)) //
            .concatWith(Mono.just(new DailyTotalEntry(null, LocalDate.ofEpochDay(-1L), -1, -1L, -1L)).repeat(10L));

        final Flux<DailyTotalEntry> entriesToSave = this.articleService.listAllArticle() //
            .flatMap(articleEntry -> this.accesscountService.calculateStats(articleEntry.getId(), date, date.plusDays(1L))) //
            .sort(Comparator.comparing(AccessStats::getTotalAccess)) //
            .take(10L) //
            .zipWith(Flux.range(1, 10)) //
            .map(tpl -> new DailyTotalEntry( //
                null, //
                date, //
                tpl.getT2(), //
                tpl.getT1().getArticleId(), //
                tpl.getT1().getTotalAccess()
            )) //
            .zipWith(existingEntries) //
            .map(tpl -> {
                if (tpl.getT2().getId() == null) {
                    return tpl.getT1();
                }
                return tpl.getT1().withId(tpl.getT2().getId());
            });

        return this.dailyTotalEntryRepository.saveAll(entriesToSave);

    }

}
