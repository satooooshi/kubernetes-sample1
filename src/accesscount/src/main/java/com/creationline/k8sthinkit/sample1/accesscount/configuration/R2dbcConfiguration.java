package com.creationline.k8sthinkit.sample1.accesscount.configuration;

import java.util.List;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter.AccesscountReadConverter;
import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter.AccesscountWriteConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.lang.NonNull;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @NonNull
    private final String url;

    @NonNull
    private final String username;

    @NonNull
    private final String password;

    public R2dbcConfiguration( //
        @NonNull @Value("${spring.r2dbc.url}") final String url, //
        @NonNull @Value("${spring.r2dbc.username}") final String username, //
        @NonNull @Value("${spring.r2dbc.password}") final String password //
    ) {

        this.url = url;
        this.username = username;
        this.password = password;

    }

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

    @Override
    protected List<Object> getCustomConverters() {

        return List.of( //
            new AccesscountReadConverter(), //
            new AccesscountWriteConverter() //
        );

    }

}
