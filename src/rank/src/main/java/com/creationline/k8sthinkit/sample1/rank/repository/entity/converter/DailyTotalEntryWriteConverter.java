package com.creationline.k8sthinkit.sample1.rank.repository.entity.converter;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.core.Parameter;

/**
 * DailyTotalEntryエンティティをR2DBCで更新/追加するためにアダプタオブジェクトに変換するコンバータ
 */
@WritingConverter
public class DailyTotalEntryWriteConverter implements Converter<DailyTotalEntry, OutboundRow> {

    /**
     * エンティティ{@link DailyTotalEntry}をアダプタオブジェクト{@link OutboundRow}に変換する
     * 
     * @param source 更新/追加するエンティティ
     */
    @Override
    @NonNull
    public OutboundRow convert( //
    
        @NonNull //
        final DailyTotalEntry source //
        
    ) {

        // create result with parameters not null on any condition
        final OutboundRow result =  new OutboundRow() //
            .append("date", Parameter.from(source.getDate())) //
            .append("rank", Parameter.from(source.getRank())) //
            .append("article_id", Parameter.from(source.getArticleId())) //
            .append("total_access", Parameter.from(source.getTotalAccess()));

        // parameter "id" exists only when updating
        if (source.getId() != null) {
            result.append("id", Parameter.from(source.getId()));
        }

        return result;

    }
    
}
