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

@Component
public class AccesscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountService.class);

    private static final String URI_TEMPLATE_FOR_CALCULATE_STATS = UriComponentsBuilder.newInstance() //
        .pathSegment("accesscounts", "stats") //
        .queryParam("article", "{articleId}") //
        .queryParam("from", "{from}") //
        .queryParam("to", "{to}") //
        .build() //
        .toString(); //

    private final DateTimeUtil dateTimeUtil;
    private final WebClient client;

    @Autowired
    public AccesscountService(

        @NonNull final DateTimeUtil dateTimeUtil, //
        @NonNull final AccesscountConfiguration accesscountConfig, //
        @NonNull final WebClient.Builder webClientBuilder

    ) {

        this.dateTimeUtil = dateTimeUtil;
        this.client = webClientBuilder.baseUrl(accesscountConfig.getUrl()) //
            .build();

    }

    public Mono<AccessStats> calculateStats( //
        @NonNull //
        final Long articleId, //
        @NonNull //
        final LocalDate start, //
        @NonNull //
        final LocalDate endExcluding //
    ) {

        LOGGER.trace("AccesscountService.calculateStats({}) called.", articleId);

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
