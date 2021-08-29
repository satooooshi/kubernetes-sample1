package com.creationline.k8sthinkit.sample1.rank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * ヘルスチェックAPI
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class HealthCheckController {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    /**
     * ヘルスチェックエンドポイント
     * @param request reqeust content
     * @return 200 OK
     */
    @GetMapping( //
        path = { //
            "healthz/", //
            "healthz" //
        } //
    )
    public Mono<ResponseEntity<?>> healthCheck( //

        @NonNull //
        final ServerHttpRequest request //

    ) {

        LOGGER.debug("access {} {} dispatched", request.getMethod(), request.getPath());

        return Mono.just(ResponseEntity.ok().build());

    }

}
