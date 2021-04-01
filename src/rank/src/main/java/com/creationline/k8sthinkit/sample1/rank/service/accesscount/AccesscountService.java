package com.creationline.k8sthinkit.sample1.rank.service.accesscount;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;

import com.creationline.k8sthinkit.sample1.rank.DateTimeUtil;
import com.creationline.k8sthinkit.sample1.rank.service.accesscount.entity.AccessStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

/**
 * アクセスカウントサービスの呼び出し
 */
@Component
public class AccesscountService {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountService.class);

    /** アクセス統計を取得するためのURIテンプレート */
    private static final String URI_TEMPLATE_FOR_CALCULATE_STATS = UriComponentsBuilder.newInstance() //
        .pathSegment("api", "accesscounts", "stats") //
        .queryParam("article", "{articleId}") //
        .queryParam("from", "{from}") //
        .queryParam("to", "{to}") //
        .build() //
        .toString(); //

    /** 日時ユーティリティ */
    private final DateTimeUtil dateTimeUtil;

    /** Accesscountサービスに接続するHTTPクライアントオブジェクト */
    private final WebClient client;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param dateTimeUtil 日時ユーティリティ
     * @param accesscountConfig Accesscountサービスへの接続設定
     * @param webClientBuilder HTTPクライアントを構築するためにSpringが提供するオブジェクト
     */
    @Autowired
    public AccesscountService(

        @NonNull //
        final DateTimeUtil dateTimeUtil, //

        @NonNull //
        final AccesscountConfiguration accesscountConfig, //

        @NonNull //
        final WebClient.Builder webClientBuilder

    ) {

        this.dateTimeUtil = dateTimeUtil;
        this.client = webClientBuilder.baseUrl(accesscountConfig.getUrl()) //
            .build();

    }

    /**
     * 指定した記事/期間のアクセス統計を取得する
     * 
     * @param articleId 記事ID
     * @param start 集計期間の開始日(期間にこの日を含む)
     * @param endExcluding 集計期間の終了日(期間にこの日を含まない)
     * @return 取得したアクセス統計
     */
    public Mono<AccessStats> calculateStats( //

        @NonNull //
        final Long articleId, //

        @NonNull //
        final LocalDate start, //

        @NonNull //
        final LocalDate endExcluding //

    ) {

        LOGGER.trace("AccesscountService.calculateStats({}) called.", articleId);

        // Accesscountサービスは時差付きの日時指定で期間の指定を受け付けるので
        // 指定された日付をその開始日時に変換してリクエストする
        final OffsetDateTime startDateTimeWithOffset = start.atStartOfDay() //
            .atOffset(this.dateTimeUtil.currentOffset());
        final OffsetDateTime endDateTimeWithOffset = endExcluding.atStartOfDay() //
            .atOffset(this.dateTimeUtil.currentOffset());
        return this.client.get() //
            .uri( //
                URI_TEMPLATE_FOR_CALCULATE_STATS, //
                Map.of(
                    "articleId", articleId, //
                    "from", startDateTimeWithOffset, //
                    "to", endDateTimeWithOffset //
                ) //
            ) //
            .accept(MediaType.APPLICATION_JSON) //
            .retrieve() //
            .bodyToMono(AccessStats.class);

    }

}
