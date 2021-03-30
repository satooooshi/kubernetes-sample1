package com.creationline.k8sthinkit.sample1.rank.repository.entity.converter;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.Row;

/**
 * DailyTotalEntryオブジェクトをR2DBCのクエリ結果から読み取るためのコンバータ
 */
@ReadingConverter
public class DailyTotalEntryReadConverter implements Converter<Row, DailyTotalEntry> {

    /**
     * R2DBCのクエリ結果({@link Row}オブジェクト)をエンティティ({@link DailyTotalEntry}オブジェクト)に変換する
     * 
     * @param source クエリ結果
     */
    @Override
    @NonNull
    public DailyTotalEntry convert( //
    
        @NonNull //
        final Row source //
        
    ) {

        return new DailyTotalEntry( //
            source.get("id", Long.class), //
            source.get("date", LocalDate.class), //
            source.get("rank", Integer.class), //
            source.get("article_id", Long.class), //
            source.get("total_access", Long.class) //
        );

    }

}
