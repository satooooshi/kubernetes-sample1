package com.creationline.k8sthinkit.sample1.article.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleEntryResponse;
import com.creationline.k8sthinkit.sample1.article.controller.response.ArticleResponse;
import com.creationline.k8sthinkit.sample1.article.repository.ArticleRepository;
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
            .map(this::convertToEnrty) //
            .collect(Collectors.toList());
    }

    @GetMapping("{article_id}/")
    public ArticleResponse getArticle( //
        @PathVariable("article_id") final Long articleId //
    ) {
        LOGGER.debug("access /{articleId}/ dispatched", articleId);
        final ArticleEntity entity = this.articleRepository.findById(articleId);
        return this.convertToArticle(entity);
    }

    private ArticleResponse convertToArticle(final ArticleEntity entity) {
        return ArticleResponse.builder() //
            .title(entity.getTitle()) //
            .author(entity.getAuthor()) //
            .body(entity.getBody()) //
            .build();
    }

    private ArticleEntryResponse convertToEnrty(final ArticleEntity entity) {
        return ArticleEntryResponse.builder() //
            .title(entity.getTitle()) //
            .author(entity.getAuthor()) //
            .build();
    }

}
