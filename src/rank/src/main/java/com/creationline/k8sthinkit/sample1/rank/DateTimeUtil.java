package com.creationline.k8sthinkit.sample1.rank;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {

    
    private final ZoneId systemZone;

    public DateTimeUtil( //
        @NonNull final ZoneId systemZone //
    ) {

        this.systemZone = systemZone;

    }

    public ZoneOffset currentOffset() {
        return this.systemZone.getRules().getOffset(Instant.now());
    }

    public OffsetDateTime toOffsetDateTimeWithSystemZone(@NonNull final LocalDateTime localdatetime) {
        return OffsetDateTime.of(localdatetime, this.currentOffset());
    }

}
