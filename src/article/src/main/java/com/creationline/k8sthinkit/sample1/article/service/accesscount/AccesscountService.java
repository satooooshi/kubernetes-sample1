package com.creationline.k8sthinkit.sample1.article.service.accesscount;

import com.creationline.k8sthinkit.sample1.article.service.accesscount.entity.Accesscount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class AccesscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountService.class);

    private final WebClient client;

    @Autowired
    public AccesscountService(

        @NonNull final AccesscountConfiguration accesscountConfig, //
        @NonNull final WebClient.Builder webClientBuilder

    ) {

        this.client = webClientBuilder.baseUrl(accesscountConfig.getUrl()) //
            .build();

    }

    public Mono<ResponseEntity<Void>> save(@NonNull final Accesscount access) {

        LOGGER.trace("AccesscountService.save({}) called.", access);

        // 単にPOSTするだけでエラーハンドリングはしない
        return this.client.post() //
            .uri("/accesscounts/") //
            .accept(MediaType.APPLICATION_JSON) //
            .contentType(MediaType.APPLICATION_JSON) //
            .bodyValue(access) //
            .retrieve() //
            .toBodilessEntity();

    }

}
