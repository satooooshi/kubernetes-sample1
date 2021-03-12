package com.creationline.k8sthinkit.sample1.article;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class NamedParameterJdbcTemplateConfig {

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(
        @Autowired final DataSource dataSource
    ) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
