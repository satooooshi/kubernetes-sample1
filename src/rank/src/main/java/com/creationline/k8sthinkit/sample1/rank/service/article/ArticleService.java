package com.creationline.k8sthinkit.sample1.rank.service.article;

import com.creationline.k8sthinkit.sample1.rank.service.article.entity.ArticleEntry;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;

/**
 * Articleサービスの呼び出し
 */
@Component
public class ArticleService {

    /** 記事一覧を取得するときのURIテンプレート */
    private static final String URI_TEMPLATE_FOR_LIST_ARTICLES = UriComponentsBuilder.newInstance() //
        .pathSegment("articles") //
        .build() //
        .toString();

    /** Articleサービスに接続するHTTPクライアントオブジェクト */
    private final WebClient client;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param articleConfig Articleサービスへの接続設定を読み込むオブジェクト
     * @param webClientBuilder HTTPクライアントを構築するためにSpringが提供するオブジェクト
     */
    public ArticleService(

        @NonNull //
        final ArticleConfiguration articleConfig, //

        @NonNull //
        final WebClient.Builder webClientBuilder //

    ) {

        this.client = webClientBuilder.baseUrl(articleConfig.getUrl()) //
            .build();

    }

    /**
     * 全記事の一覧を取得する
     * 
     * @return 処理時点の記事
     */
    public Flux<ArticleEntry> listAllArticle() {
        return this.client.get() //
            .uri(URI_TEMPLATE_FOR_LIST_ARTICLES) //
            .accept(MediaType.APPLICATION_JSON) //
            .retrieve() //
            .bodyToFlux(ArticleEntry.class);
    }

}
