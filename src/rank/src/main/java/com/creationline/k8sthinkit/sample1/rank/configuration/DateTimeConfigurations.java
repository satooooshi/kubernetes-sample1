package com.creationline.k8sthinkit.sample1.rank.configuration;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class DateTimeConfigurations {

    @Bean
    public ZoneId systemZone( //
        @Value("${timezone:}") //
        @NonNull //
        final String zoneId //
    ) {

        if (zoneId.isEmpty()) {
            return ZoneId.systemDefault();
        }
        return ZoneId.of(zoneId);

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
