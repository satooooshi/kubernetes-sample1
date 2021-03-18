package com.creationline.k8sthinkit.sample1.article.service.accesscount;

import com.creationline.k8sthinkit.sample1.article.service.accesscount.entity.Accesscount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class AccesscountService {

    private final WebClient client;

    @Autowired
    public AccesscountService(

        @NonNull final AccesscountConfiguration accesscountConfig, //
        @NonNull final WebClient.Builder webClientBuilder

    ) {

        this.client = webClientBuilder.baseUrl(accesscountConfig.getUrl()) //
            .build();

    }

    public void save(@NonNull final Accesscount access) {

        // 単にPOSTするだけでエラーハンドリングはしない
        this.client.post() //
            .uri("/accesscount/") //
            .accept(MediaType.APPLICATION_JSON) //
            .contentType(MediaType.APPLICATION_JSON) //
            .bodyValue(access)
            .exchangeToMono((resp) -> {
                return resp.toEntity(Accesscount.class);
            });

    }

}
