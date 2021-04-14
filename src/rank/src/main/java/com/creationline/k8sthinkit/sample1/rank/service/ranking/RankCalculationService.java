package com.creationline.k8sthinkit.sample1.rank.service.ranking;

import java.time.LocalDate;
import java.util.Comparator;

import com.creationline.k8sthinkit.sample1.rank.FunctionUtil;
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
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * アクセスランキングを集計するクラス
 */
@Component
public class RankCalculationService {

    /** 最大順位 */
    private static final long MAX_RANK = 10;

    /** アクセスカウントサービス */
    private final AccesscountService accesscountService;

    /** 記事サービス */
    private final ArticleService articleService;

    /** 日次延べアクセス順位を永続化するリポジトリ */
    private final DailyTotalEntryRepository dailyTotalEntryRepository;

    /** 日次アクセスユーザ順位を永続化するリポジトリ */
    private final DailyUniqueEntryRepository dailyUniqueEntryRepository;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     */
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

    /**
     * 日次アクセスユーザ順位を更新する
     * 
     * @param date 順位を更新する日付
     * @return 更新された順位
     */
    public Flux<DailyUniqueEntry> updateDailyUniqueAccessRank( //

        @NonNull //
        final LocalDate date //

    ) {

        // reactive stream holding existing entries
        final Flux<DailyUniqueEntry> existingEntries = this.dailyUniqueEntryRepository.findByDate(date) //

            // sort by rank
            .sort(Comparator.comparing(DailyUniqueEntry::getRank)) //

            // append invalid entry records (ignored when conbining new records)
            .concatWith( //
                Mono.just(new DailyUniqueEntry(null, LocalDate.ofEpochDay(-1L), -1, -1L, -1L, "", "")) //
                    .repeat(MAX_RANK) //
            );

        final Flux<DailyUniqueEntry> entriesToSave = this.articleService.listAllArticle() //

            // (ArticleEntry) -> (ArticleEntry, AccessStats)
            .flatMap(articleEntry -> {
                final Mono<AccessStats> stats = this.accesscountService.calculateStats(articleEntry.getId(), date, date.plusDays(1L));
                return Mono.just(articleEntry) //
                    .zipWith(stats);
            }) //

            // sort by (ArticleEntry, AccessStats)[1].totalAccess
            .sort(FunctionUtil.comparing(Tuple2::getT2, AccessStats::getUniqueAccess)) //

            .take(MAX_RANK) //

            // (ArticleEntry, AccessStats)
            //   -> ((ArticleEntry, AccessStats), Integer)
            .zipWith(Flux.range(1, (int) MAX_RANK)) //

            // ((ArticleEntry, AccessStats), Integer)
            //   -> (ArticleEntry, AccessStats, Integer)
            .map(nestedTuple -> Tuples.of( //
                nestedTuple.getT2(), //
                nestedTuple.getT1().getT1(), //
                nestedTuple.getT1().getT2() //
            )) //

            // (ArticleEntry, AccessStats, Integer)
            //   -> DailyUniqueEntry
            .map(tpl -> new DailyUniqueEntry( //
                null, //
                date, //
                tpl.getT1(), //
                tpl.getT3().getArticleId(), //
                tpl.getT3().getUniqueAccess(), //
                tpl.getT2().getTitle(), //
                tpl.getT2().getAuthor() //
            )) //

            // DailyUniqueEntry
            //   -> (DailyUniqueEntry (new), DailyUniqueEntry (existing))
            .zipWith(existingEntries) //

            // combine entities into one to update if record exists, or insert new record otherwise
            .map(tpl -> {
                // if existing entity have no id, new entity used
                if (tpl.getT2().getId() == null) {
                    return tpl.getT1();
                }
                // otherwise, copy id from existing entity to new entity (to update existing record)
                return tpl.getT1().withId(tpl.getT2().getId());
            });

        return this.dailyUniqueEntryRepository.saveAll(entriesToSave);

    }

    /**
     * 日次延べアクセス順位を更新する
     * 
     * @param date 順位を更新する日付
     * @return 更新された順位
     */
    public Flux<DailyTotalEntry> updateDailyTotalAccessRank( //

        @NonNull //
        final LocalDate date //

    ) {

        // reactive stream holding existing entries
        final Flux<DailyTotalEntry> existingEntries = this.dailyTotalEntryRepository.findByDate(date) //

            // sort by rank
            .sort(Comparator.comparing(DailyTotalEntry::getRank)) //

            // append invalid entry records (ignored when conbining new records)
            .concatWith( //
                Mono.just(new DailyTotalEntry(null, LocalDate.ofEpochDay(-1L), -1, -1L, -1L, "", "")) //
                    .repeat(MAX_RANK) //
            );

        final Flux<DailyTotalEntry> entriesToSave = this.articleService.listAllArticle() //

            // (ArticleEntry) -> (ArticleEntry, AccessStats)
            .flatMap(articleEntry -> {
                final Mono<AccessStats> stats = this.accesscountService.calculateStats(articleEntry.getId(), date, date.plusDays(1L));
                return Mono.just(articleEntry) //
                    .zipWith(stats);
            }) //

            // sort by (ArticleEntry, AccessStats)[1].totalAccess
            .sort(FunctionUtil.comparing(Tuple2::getT2, AccessStats::getTotalAccess)) //

            .take(MAX_RANK) //

            // (ArticleEntry, AccessStats)
            //   -> ((ArticleEntry, AccessStats), Integer)
            .zipWith(Flux.range(1, (int) MAX_RANK)) //

            // ((ArticleEntry, AccessStats), Integer)
            //   -> (ArticleEntry, AccessStats, Integer)
            .map(nestedTuple -> Tuples.of( //
                nestedTuple.getT2(), //
                nestedTuple.getT1().getT1(), //
                nestedTuple.getT1().getT2() //
            )) //

            // (ArticleEntry, AccessStats, Integer)
            //   -> DailyTotalEntry
            .map(tpl -> new DailyTotalEntry( //
                null, //
                date, //
                tpl.getT1(), //
                tpl.getT3().getArticleId(), //
                tpl.getT3().getTotalAccess(), //
                tpl.getT2().getTitle(), //
                tpl.getT2().getAuthor() //
            )) //

            // DailyTotalEntry
            //   -> (DailyTotalEntry (new), DailyTotalEntry (existing))
            .zipWith(existingEntries) //

            // combine entities into one to update if record exists, or insert new record otherwise
            .map(tpl -> {
                // if existing entity have no id, new entity used
                if (tpl.getT2().getId() == null) {
                    return tpl.getT1();
                }
                // otherwise, copy id from existing entity to new entity (to update existing record)
                return tpl.getT1().withId(tpl.getT2().getId());
            });

        return this.dailyTotalEntryRepository.saveAll(entriesToSave);

    }

}
