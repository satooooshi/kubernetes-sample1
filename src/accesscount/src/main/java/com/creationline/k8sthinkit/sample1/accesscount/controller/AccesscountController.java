package com.creationline.k8sthinkit.sample1.accesscount.controller;

import java.time.OffsetDateTime;
import java.util.Set;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * アクセスカウントAPI
 */
@RestController
@RequestMapping("/api/accesscounts")
@CrossOrigin
public class AccesscountController {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountController.class);

    /** Accesscountエンティティを管理するRepositoryオブジェクト */
    private final AccesscountRepository accesscountRepository;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param accesscountRepository Accesscountエンティティを管理するRepositoryクラス
     */
    @Autowired
    public AccesscountController( //

        @NonNull //
        final AccesscountRepository accesscountRepository //

    ) {

        this.accesscountRepository = accesscountRepository;

    }

    /**
     * 1回のアクセスを記録するAPI
     * 
     * @param accessRecord アクセス内容
     */
    @PostMapping( //
        path = {
            "/", //
            "" //
        }, //
        consumes = Controllers.MIMETYPE_CONSUMING //
    )
    public Mono<ResponseEntity<?>> recordAccess( //

        @RequestBody //
        final Mono<AccessRecordRequest> accessRecord //

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
        LOGGER.trace("  arguments: article: {}", articleId);
        LOGGER.trace("  arguments: from   : {}", from);
        LOGGER.trace("  arguments: to     : {}", to);

        /*
         * 延べアクセス数とユニークアクセス数を計算する.
         * クエリメソッドからの戻り値は単一の記事IDのアクセスのみなので異なる記事へのアクセスレコードは含まれない.
         * アクセス記録のUIDだけを抽出する. この時重複排除(distinct()メソッドなど)を行わない.
         * 結果をcollectList()メソッドで重複を保ったままListに格納する.
         * 最後にアクセス数を計算する. 述べアクセス数はList.size()で計算され,
         * Listの要素はユーザを識別する文字列なのでList全体をSetへ変換してsize()メソッドを呼ぶことでユニークアクセス数が計算される.
         */
        final Flux<Accesscount> rawRecords = this.accesscountRepository.findByArticleIdAndAccessAtInTerm(articleId, from, to); //
        return rawRecords.map(Accesscount::getUid) //
            .collectList() //
            .map(uids -> ResponseEntity.ok( //
                new AccessStatsResponse( //
                    articleId, //
                    Long.valueOf(uids.size()), //
                    Long.valueOf(Set.copyOf(uids).size()), //
                    from, //
                    to //
                ) //
            ));

    }

    /**
     * 受け取ったアクセス内容({@code AccesRecord}オブジェクト)を永続化するアクセスカウント({@code Accesscount}オブジェクト)に変換する
     * 
     * 新規エンティティなのでIDフィールドは{@code null}とする
     */
    private Accesscount convertAccessRecordToAccesscountDraft(@NonNull final AccessRecordRequest accessRecord) {
        return new Accesscount(
            null, //
            accessRecord.getArticleId(), //
            accessRecord.getUid(), //
            accessRecord.getAccessAt() //
        );
    }

}
