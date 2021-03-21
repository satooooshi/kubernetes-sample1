package com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter;

import java.time.OffsetDateTime;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.Row;

@ReadingConverter
public class AccesscountReadConverter implements Converter<Row, Accesscount> {

    @Override
    public Accesscount convert( //
        @NonNull final Row source //
    ) {
        return new Accesscount( //
            source.get("id", Long.class), //
            source.get("article_id", Long.class), //
            source.get("uid", String.class), //
            source.get("access_at", OffsetDateTime.class) //
        );
    }

}
