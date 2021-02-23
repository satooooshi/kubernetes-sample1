package com.creationline.k8sthinkit.sample1.article.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.context.request.WebRequest;

import com.creationline.k8sthinkit.sample1.article.controller.request.ArticleCreationRequest;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleEntryResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleNotFoundResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleResponse;
import com.creationline.k8sthinkit.sample1.article.repository.ArticleRepository;
import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleDraft;
import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleEntity;

@RestController()
@RequestMapping("/")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(
        final ArticleRepository articleRepository
    ) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("list/")
    public List<ArticleEntryResponse> getAllArticles() {
        LOGGER.debug("access /list/ dispatched");
        final List<ArticleEntity> entities = this.articleRepository.findAll();
        return entities.stream() //
            .map(this::convertToEntry) //
            .collect(Collectors.toList());
    }

    @GetMapping("{article_id}/")
    public ArticleResponse getArticle( //
        @PathVariable("article_id") final Long articleId //
    ) throws ArticleNotFoundException {
        LOGGER.debug("access /{articleId}/ dispatched", articleId);
        final Optional<ArticleEntity> entity = this.articleRepository.findById(articleId);
        if (entity.isEmpty()) {
            throw new ArticleNotFoundException("requested article (id: " + articleId + ") not found.");
        }
        return this.convertToArticle(entity.get());
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@NonNull final HttpServletRequest request, @RequestBody @NonNull final ArticleCreationRequest creationRequest) {
        final ArticleDraft articleDraft = this.convertToDraft(creationRequest);
        final ArticleEntity article = this.articleRepository.save(articleDraft);
        final String createdUrl = request.getRequestURL().append(article.getId()).append("/").toString();
        return ResponseEntity.created(URI.create(createdUrl)) //
            .body(this.convertToArticle(article));
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

    private ArticleResponse convertToArticle(final ArticleDraft draft) {
        return ArticleResponse.builder() //
            .title(draft.getTitle()) //
            .author(draft.getAuthor()) //
            .body(draft.getBody()) //
            .build();
    }

    private ArticleEntryResponse convertToEntry(final ArticleEntity entity) {
        return ArticleEntryResponse.builder() //
            .title(entity.getTitle()) //
            .author(entity.getAuthor()) //
            .build();
    }

    private ArticleDraft convertToDraft(@NonNull final ArticleCreationRequest creationRequest) {
        return new ArticleDraft( //
            creationRequest.getTitle() //
            , creationRequest.getAuthor() //
            , creationRequest.getBody());
    }

}
