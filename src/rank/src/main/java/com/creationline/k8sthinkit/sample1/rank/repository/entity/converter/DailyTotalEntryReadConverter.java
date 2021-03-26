package com.creationline.k8sthinkit.sample1.rank.repository.entity.converter;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyEntryId;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.Row;

@ReadingConverter
public class DailyTotalEntryReadConverter implements Converter<Row, DailyTotalEntry> {

    @Override
    @NonNull
    public DailyTotalEntry convert(@NonNull final Row source) {

        return new DailyTotalEntry( //
            new DailyEntryId( //
                source.get("date", LocalDate.class), //
                source.get("rank", Integer.class) //
            ), //
            source.get("article_id", Long.class), //
            source.get("total_access", Long.class) //
        );

    }

}
