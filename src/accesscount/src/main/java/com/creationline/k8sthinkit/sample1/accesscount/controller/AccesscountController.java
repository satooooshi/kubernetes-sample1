package com.creationline.k8sthinkit.sample1.accesscount.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.creationline.k8sthinkit.sample1.accesscount.controller.request.AccessRecordRequest;
import com.creationline.k8sthinkit.sample1.accesscount.controller.response.AccessStatsResponse;
import com.creationline.k8sthinkit.sample1.accesscount.repository.AccesscountRepository;
import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * アクセスカウントAPI
 */
@RestController
@RequestMapping("/accesscounts")
public class AccesscountController {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountController.class);
    private final AccesscountRepository accesscountRepository;
    @Autowired
    public AccesscountController(
        @NonNull final AccesscountRepository accesscountRepository //
    ) {
        this.accesscountRepository = accesscountRepository;
    }

    /**
     * 1回のアクセスを記録するAPI
     */
    @PostMapping( //
        path = {
            "/", //
            "" //
        }, //
        consumes = Controllers.MIMETYPE_CONSUMING //
    )
    public Mono<ResponseEntity<?>> recordAccess(

        @RequestBody final Mono<AccessRecordRequest> accessRecord //

    ) {

        LOGGER.debug("access POST /accesscounts/ dispatched");

        return accessRecord.map(this::convertAccessRecordToAccesscountDraft) //
            .flatMap(this.accesscountRepository::save) //
            .map((__) -> ResponseEntity.noContent().build());

    }

    /**
     * 記事のアクセス数集計を取得する.
     * 
     * 集計期間は, 指定された開始日時と終了日時は開始日時を含み終了日時を含まない半開区間として集計する. これにより、「期間(A, B)」と「期間(B,
     * C)」の述べアクセス数の単純な合計が「期間(A, C)」の述べアクセス数とできる. (A, B, Cは「 A < B < C 」となるものとする.)
     * 
     * @param articleId 集計対象の記事ID
     * @param from      集計する期間の開始日時.
     * @param to        集計する期間の終了日時. 指定された時刻は含まれない.
     */
    @GetMapping( //
        path = { //
            "/stats/", //
            "/stats" //
        }, //
        params = { //
            "article", //
            "from", //
            "to" //
        } //
    )
    public Mono<ResponseEntity<AccessStatsResponse>> stats( //

        @RequestParam("article") //
        final Long articleId, //
 
        @RequestParam("from") //
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //
        final OffsetDateTime from, //

        @RequestParam("to") //
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //
        final OffsetDateTime to //

    ) {

        LOGGER.debug("access GET /accesscounts/stats/ dispatched");
        LOGGER.debug("  arguments: article: {}", articleId);
        LOGGER.debug("  arguments: from   : {}", from);
        LOGGER.debug("  arguments: to     : {}", to);

        // TODO: get actual statistics
        return Mono.just( //
            ResponseEntity.ok( //
                new AccessStatsResponse( //
                    1L, //
                    10L, //
                    3L, //
                    OffsetDateTime.of(2021, 1, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")), //
                    OffsetDateTime.of(2021, 2, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00")) //
                )));

    }

    private Accesscount convertAccessRecordToAccesscountDraft(@NonNull final AccessRecordRequest accessRecord) {
        return new Accesscount(
            // Draft has no ID persisted
            null, //
            accessRecord.getArticleId(), //
            accessRecord.getUid(), //
            accessRecord.getAccessAt() //
        );
    }

}
