package com.creationline.k8sthinkit.sample1.rank.repository.entity.converter;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyEntryId;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.Row;

@ReadingConverter
public class DailyUniqueEntryReadConverter implements Converter<Row, DailyUniqueEntry> {

    @Override
    @NonNull
    public DailyUniqueEntry convert(@NonNull final Row source) {

        return new DailyUniqueEntry( //
            new DailyEntryId( //
                source.get("date", LocalDate.class), //
                source.get("rank", Integer.class) //
            ), //
            source.get("article_id", Long.class), //
            source.get("unique_access", Long.class) //
        );

    }

}
