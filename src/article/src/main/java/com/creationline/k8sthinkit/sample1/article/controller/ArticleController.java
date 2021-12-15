package com.creationline.k8sthinkit.sample1.article.controller;

import java.net.URI;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

import com.creationline.k8sthinkit.sample1.article.controller.request.ArticleCreationRequest;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleEntryResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleNotFoundResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleResponse;
import com.creationline.k8sthinkit.sample1.article.repository.ArticleRepository;
import com.creationline.k8sthinkit.sample1.article.repository.entity.Article;
import com.creationline.k8sthinkit.sample1.article.service.accesscount.AccesscountService;
import com.creationline.k8sthinkit.sample1.article.service.accesscount.entity.Accesscount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 記事API
 */
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    /** 記事の情報を永続化するコンポーネント */
    private final ArticleRepository articleRepository;

    private final AccesscountService accesscountService;

    private final Clock clock;

    /**
     * コンストラクターインジェクションによりDIコンテナから必要なコンポーネントを取得する
     * 
     * @param articleRepository
     */
    @Autowired
    public ArticleController( //

        final ArticleRepository articleRepository, //
        final AccesscountService accesscountService, //
        final Clock clock //

    ) {

        this.articleRepository = articleRepository;
        this.accesscountService = accesscountService;
        this.clock = clock;

    }


    /**
     * すべての記事の一覧を取得するAPI.
     * {@code /articles/}への{@code GET}リクエストを処理する.
     * 
     * @return すべての記事の一覧
     */
    @GetMapping( //
        path = { //
            "/", //
            "" //
        }, //
        produces = Controllers.MIMETYPE_PRODUCING // 応答するMIMETYPE
    )
    public Flux<ArticleEntryResponse> getAllArticles(

        @NonNull //
        final ServerHttpRequest request //

    ) {

        LOGGER.debug("access {} {} dispatched", request.getMethod(), request.getPath());

        // 永続化層 (ArticleRepository) の一覧メソッドでArticleをストリームとして取得し
        // convertToEntry変換したストリームで応答する。
        return this.articleRepository.findAll() //
            .map(this::convertToEntry);

    }


    /**
     * 個別の記事をID指定で取得するAPI.
     * {@code /articles/{article_id}/}への{@code GET}リクエストを処理する.
     * 
     * @param articleId 取得する記事のID。パス変数 {@code article_id} の値を取得する.
     * @return 該当するIDの記事内容.
     * @throws ArticleNotFoundException 指定されたIDの記事が存在しない場合. ステータスコード404.
     */
    @GetMapping( //
        path = { //
            "/{article_id}/", //
            "/{article_id}" //
        }, //
        produces = Controllers.MIMETYPE_PRODUCING // 応答するMIMETYPE
    )
    public Mono<ResponseEntity<ArticleResponse>> getArticle( //

        @NonNull //
        final ServerHttpRequest request, //

        @PathVariable("article_id") //
        @NonNull //
        final Long articleId, //

        @RequestHeader( //
            name = "X-UID", //
            required = false //
        ) //
        @NonNull //
        final Optional<String> uid //
    
    ) throws ArticleNotFoundException {

        LOGGER.debug("access {} {} dispatched", request.getMethod(), request.getPath());

        // X-UIDヘッダの値が存在するときはaccesscountServiceにアクセスがあったことを通知する
        if (uid.isPresent() && !uid.get().isBlank()) {
            this.accesscountService.save(new Accesscount( //
                articleId, //
                uid.get(), //
                OffsetDateTime.now(this.clock) //
            )) //
            .doOnError((error) -> {
                LOGGER.error("access notification to accesscount failed.", error);
            })
            .subscribe();
        }

        // 永続化層 (ArticleRepository) の取得メソッドでArticleを取得し
        // ArticleResponseへ変換とResponseEntityでの包み込みを行う。
        return this.articleRepository.findById(articleId) //
            .map(this::convertToArticle) //
            .map(ResponseEntity.ok()::body) //
            // 指定されたIDが存在しないと空のレスポンスを正常ステータスで返してしまうので
            // ArticleNotFoundExceptionを発生する例外ストリームに切り替える。
            .switchIfEmpty(Mono.error(() -> new ArticleNotFoundException("requested article (id: " + articleId + ") not found.")));

    }


    /**
     * 記事の新規作成API.
     * {@code /articles/}への{@code POST}リクエストを処理する.
     * 
     * @param creationRequest 作成する記事の内容
     * @return 作成された記事とそれにアクセスするためのURL
     */
    @PostMapping( //
        path = { //
            "/", //
            "" //
        }, //
        consumes = Controllers.MIMETYPE_CONSUMING, // 受け付けるMIMETYPE
        produces = Controllers.MIMETYPE_PRODUCING // 応答するMIMETYPE
    )
    public Mono<ResponseEntity<ArticleResponse>> createArticle(

        @NonNull //
        final ServerHttpRequest request, //

        @RequestBody //
        Mono<ArticleCreationRequest> creationRequest //
    
    ) {

        LOGGER.debug("access {} {} dispatched", request.getMethod(), request.getPath());

        return creationRequest.map(this::convertToDraft) //
            .flatMap(this.articleRepository::save) //
            .map(entity -> {
                final String createdURL = "/articles/" + entity.getId() + "/";
                return ResponseEntity.created(URI.create(createdURL)) //
                    .body(this.convertToArticle(entity));
            });

    }


    /**
     * 指定された記事が見つからなかったときの例外ハンドラ.
     * HTTPステータス404で応答する.
     * 
     * @param exception 例外
     * @return 例外応答
     */
    @ExceptionHandler
    public ResponseEntity<ArticleNotFoundResponse> handleArticleNotFound(
        
        @NonNull final ArticleNotFoundException exception
    
    ) {

        // 例外メッセージをbodyに設定して応答する
        return ResponseEntity.status(HttpStatus.NOT_FOUND) //
            .body(new ArticleNotFoundResponse(exception.getMessage()));

    }


    /**
     * 永続化エンティティ{@code Article}をレスポンスBodyとなる{@code ArticleResponse}に変換する.
     * 
     * @param entity 変換する永続化エンティティ
     * @return 変換したレスポンスBody
     */
    private ArticleResponse convertToArticle(@NonNull final Article entity) {

        return new ArticleResponse( //
            entity.getTitle(), //
            entity.getAuthor(), //
            entity.getBody() //
        );

    }


    /**
     * 永続化エンティティ{@code Article}を一覧要素となる{@code ArticleEntryResponse}に変換する.
     * 
     * @param entity 変換する永続化エンティティ
     * @return 変換した一覧要素
     */
    private ArticleEntryResponse convertToEntry(@NonNull final Article entity) {

        return new ArticleEntryResponse( //
            entity.getId(), //
            entity.getTitle(), //
            entity.getAuthor() //
        );

    }


    /**
     * 新規記事の作成リクエスト本文を新しい永続化エンティティに変換します.
     * 
     * @param creationRequest 変換する記事作成リクエスト
     * @return 新しい永続化エンティティ
     */
    private Article convertToDraft(@NonNull final ArticleCreationRequest creationRequest) {

        return new Article( //
            // Draft has no ID persisted
            null, //
            creationRequest.getTitle(), //
            creationRequest.getAuthor(), //
            creationRequest.getBody() //
        );

    }

}