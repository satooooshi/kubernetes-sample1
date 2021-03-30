package com.creationline.k8sthinkit.sample1.rank.configuration;

import java.util.List;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.converter.DailyTotalEntryReadConverter;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.converter.DailyTotalEntryWriteConverter;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.converter.DailyUniqueEntryReadConverter;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.converter.DailyUniqueEntryWriteConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

/**
 * Spring Data R2DBCの設定クラス
 * 
 * R2DBCリポジトリで使用されるCustomConverterを登録するために独自実装を提供する
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    /** プロパティ参照 spring.r2dbc.url */
    @NonNull
    private final String url;

    /** プロパティ参照 spring.r2dbc.username */
    @NonNull
    private final String username;

    /** プロパティ参照 spring.r2dbc.username */
    @NonNull
    private final String password;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param url プロパティ参照 spring.r2dbc.url
     * @param username プロパティ参照 spring.r2dbc.username
     * @param password プロパティ参照 spring.r2dbc.password
     */
    public R2dbcConfiguration( //

        @NonNull //
        @Value("${spring.r2dbc.url}") //
        final String url, //

        @NonNull //
        @Value("${spring.r2dbc.username}") //
        final String username, //

        @NonNull //
        @Value("${spring.r2dbc.password}") //
        final String password //

    ) {

        this.url = url;
        this.username = username;
        this.password = password;

    }

    /**
     * ConnectionFactoryの作成
     * 
     * デフォルトのConnectionFactoryと同等にプロパティから読み込んだURL/user名/パスワードを設定している
     */
    @Override
    public ConnectionFactory connectionFactory() {

        final ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(this.url);

        return ConnectionFactories.get( //
            baseOptions.mutate() //
                .option(ConnectionFactoryOptions.USER, this.username) //
                .option(ConnectionFactoryOptions.PASSWORD, this.password) //
                .build() //
        );

    }

    /**
     * エンティティをSQLクエリのためのオブジェクトへの変換(読み取り/書き込みの2通り)をするConverterを登録する
     */
    @Override
    protected List<Object> getCustomConverters() {

        return List.of( //
            new DailyTotalEntryReadConverter(), //
            new DailyTotalEntryWriteConverter(), //
            new DailyUniqueEntryReadConverter(), //
            new DailyUniqueEntryWriteConverter() //
        );

    }

}
