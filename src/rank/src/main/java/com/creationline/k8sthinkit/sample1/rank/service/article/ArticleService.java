package com.creationline.k8sthinkit.sample1.rank.service.article;

import com.creationline.k8sthinkit.sample1.rank.service.article.entity.ArticleEntry;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;

@Component
public class ArticleService {

    private static final String URI_TEMPLATE_FOR_LIST_ARTICLES = UriComponentsBuilder.newInstance() //
        .pathSegment("articles") //
        .build() //
        .toString();

    private final WebClient client;

    public ArticleService(
        @NonNull //
        final ArticleConfiguration articleConfig, //
        @NonNull //
        final WebClient.Builder webClientBuilder //
    ) {
        this.client = webClientBuilder.baseUrl(articleConfig.getUrl()) //
            .build();
    }

    public Flux<ArticleEntry> listAllArticle() {
        return this.client.get() //
            .uri(URI_TEMPLATE_FOR_LIST_ARTICLES) //
            .accept(MediaType.APPLICATION_JSON) //
            .retrieve() //
            .bodyToFlux(ArticleEntry.class);
    }

}
