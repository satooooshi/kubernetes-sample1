package com.creationline.k8sthinkit.sample1.article.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArticleRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleRepository.class);

    private static final String SQL_FIND_ALL = "SELECT id, title, author, body FROM article";
    private static final String SQL_FIND_ARTICLE_BY_ID = "SELECT id, title, author, body FROM article WHERE id = :id";
    private static final String SQL_FIND_ARTICLE_BY_AUTHOR = "SELECT id, title, author, body FROM article WHERE author = :author";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepository(
        final NamedParameterJdbcTemplate jdbcTeplate
    ) {
        this.jdbcTemplate = jdbcTeplate;
    }

    @Transactional
    public List<ArticleEntity> findAll() {
        return this.jdbcTemplate.query(SQL_FIND_ALL, ArticleRepository.getRowMapper());
    }

    @Transactional
    public ArticleEntity findById(final Long id) {
        return this.jdbcTemplate.query(SQL_FIND_ARTICLE_BY_ID, Map.of("id", id), ArticleRepository.getResultSetExtractor());
    }

    @Transactional
    public List<ArticleEntity> findByAuthor(final String author) {
        return this.jdbcTemplate.query(SQL_FIND_ARTICLE_BY_AUTHOR, Map.of("author", author), ArticleRepository.getRowMapper());
    }

    private static ArticleEntity map(final ResultSet rs) throws SQLException {
        return new ArticleEntity( //
            rs.getLong("id") //
            , rs.getString("title") //
            , rs.getString("author") //
            , rs.getString("body") //
        );
    }
    private static ResultSetExtractor<ArticleEntity> getResultSetExtractor() {
        return new ResultSetExtractor<ArticleEntity>() {
            @Override
            public ArticleEntity extractData(final ResultSet rs) throws SQLException {
                return ArticleRepository.map(rs);
            }
        };
    }
    private static RowMapper<ArticleEntity> getRowMapper() {
        return new RowMapper<ArticleEntity>() {
            @Override
            public ArticleEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                return ArticleRepository.map(rs);
            }
        };
    }

}
