package com.creationline.k8sthinkit.sample1.article.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.creationline.k8sthinkit.sample1.article.controller.request.ArticleCreationRequest;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleEntryResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleNotFoundResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleResponse;
import com.creationline.k8sthinkit.sample1.article.repository.ArticleRepository;
import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleEntity;

@RestController()
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private static final String MIMETYPE_CONSUMING = "application/json";
    private static final String MIMETYPE_PRODUCING = "application/json";

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(
        final ArticleRepository articleRepository
    ) {
        this.articleRepository = articleRepository;
    }

    @GetMapping(path = {"/"}, produces = {MIMETYPE_PRODUCING})
    public Flux<ArticleEntryResponse> getAllArticles() {
        LOGGER.debug("access /list/ dispatched");
        return this.articleRepository.findAll() //
            .map(this::convertToEntry);
    }

    @GetMapping(path = {"/{article_id}/"}, produces = {MIMETYPE_PRODUCING})
    public Mono<ResponseEntity<ArticleResponse>> getArticle( //
        @PathVariable("article_id") final Long articleId //
    ) throws ArticleNotFoundException {
        LOGGER.debug("access /{articleId}/ dispatched", articleId);
        return this.articleRepository.findById(articleId) //
            .map(this::convertToArticle) //
            .map(ResponseEntity.ok()::body) //
            .switchIfEmpty(Mono.error(() -> new ArticleNotFoundException("requested article (id: " + articleId + ") not found.")));
    }

    @PostMapping(path = {"/"}, consumes = {MIMETYPE_CONSUMING}, produces = {MIMETYPE_PRODUCING})
    public Mono<ResponseEntity<ArticleResponse>> createArticle(@RequestBody Mono<ArticleCreationRequest> creationRequest) {
        final Mono<ArticleEntity> articleDraft = creationRequest.map(this::convertToDraft);
        final Mono<ArticleEntity> article = this.articleRepository.saveAll(articleDraft) //
            .next();
        //final String createdUrl = "/articles/" + article.getId() + "/";
        return article.map(entity -> {
            final String createdURL = "/articles/" + entity.getId() + "/";
            return ResponseEntity.created(URI.create(createdURL)) //
                .body(this.convertToArticle(entity));
        });
    }

    @ExceptionHandler
    public ResponseEntity<ArticleNotFoundResponse> handleArticleNotFound(@NonNull final ArticleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) //
            .body(new ArticleNotFoundResponse(exception.getMessage()));
    }

    private ArticleResponse convertToArticle(final ArticleEntity entity) {
        return ArticleResponse.builder() //
            .title(entity.getTitle()) //
            .author(entity.getAuthor()) //
            .body(entity.getBody()) //
            .build();
    }

    private ArticleEntryResponse convertToEntry(final ArticleEntity entity) {
        return ArticleEntryResponse.builder() //
            .title(entity.getTitle()) //
            .author(entity.getAuthor()) //
            .build();
    }

    private ArticleEntity convertToDraft(@NonNull final ArticleCreationRequest creationRequest) {
        return new ArticleEntity( //
            // Draft has no ID persisted
            null //
            , creationRequest.getTitle() //
            , creationRequest.getAuthor() //
            , creationRequest.getBody());
    }

}
