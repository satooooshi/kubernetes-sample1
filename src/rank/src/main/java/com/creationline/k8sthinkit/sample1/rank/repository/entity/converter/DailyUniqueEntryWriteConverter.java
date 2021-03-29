package com.creationline.k8sthinkit.sample1.rank.repository.entity.converter;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class DailyUniqueEntryWriteConverter implements Converter<DailyUniqueEntry, OutboundRow> {

    @Override
    @NonNull
    public OutboundRow convert(@NonNull final DailyUniqueEntry source) {

        final OutboundRow outboundRow = new OutboundRow() //
            .append("date", Parameter.from(source.getDate())) //
            .append("rank", Parameter.from(source.getRank())) //
            .append("article_id", Parameter.from(source.getArticleId())) //
            .append("unique_access", Parameter.from(source.getUniqueAccess()));

        if (source.getId() != null) {
            outboundRow.append("id", Parameter.from(source.getId()));
        }

        return outboundRow;

    }
    
}
