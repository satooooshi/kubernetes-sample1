package com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter;

import java.util.Map;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class AccesscountWriteConverter implements Converter<Accesscount, OutboundRow> {

    @NonNull
    public OutboundRow convert( //
        @NonNull final Accesscount source //
    ) {

        // create result with parameters not null on any condition
        final OutboundRow result = new OutboundRow(Map.of( //
            "article_id", Parameter.from(source.getArticleId()), //
            "uid", Parameter.from(source.getUid()), //
            "access_at", Parameter.from(source.getAccessAt()) //
        ));

        // parameter "id" exists only when updating
        if (source.getId() != null) {
            result.append("id", Parameter.from(source.getId()));
        }

        return result;

    }

}
