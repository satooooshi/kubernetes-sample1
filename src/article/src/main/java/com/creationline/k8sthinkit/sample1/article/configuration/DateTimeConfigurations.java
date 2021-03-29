package com.creationline.k8sthinkit.sample1.article.configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class DateTimeConfigurations {

    @Bean
    public ZoneId systemZone() {

        return ZoneId.of("Asia/Tokyo");

    }

    @Bean
    public ZoneOffset zoneOffset( //
        @Autowired //
        @NonNull //
        final ZoneId systemZone //
    ) {

        return systemZone.getRules().getOffset(LocalDateTime.now());

    }

    @Bean
    public Clock clock(
        @Autowired //
        @NonNull //
        final ZoneId systemZone //
    ) {

        return Clock.system(systemZone);

    }

}
