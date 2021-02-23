package com.creationline.k8sthinkit.sample1.article.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleDraft;
import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArticleRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleRepository.class);

    private static final String SQL_FIND_ALL = "SELECT id, title, author, body FROM article";
    private static final String SQL_FIND_ARTICLE_BY_ID = "SELECT id, title, author, body FROM article WHERE id = :id";
    private static final String SQL_FIND_ARTICLE_BY_AUTHOR = "SELECT id, title, author, body FROM article WHERE author = :author";
    private static final String SQL_SAVE_ARTICLE = "INSERT INTO article (title, author, body) VALUES (:title, :author, :body) RETURNING id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepository(
        final NamedParameterJdbcTemplate jdbcTeplate
    ) {
        this.jdbcTemplate = jdbcTeplate;
    }

    @Transactional
    @NonNull
    public List<ArticleEntity> findAll() {
        LOGGER.debug("execute SQL: {}", SQL_FIND_ALL);
        return this.jdbcTemplate.query(SQL_FIND_ALL, (rs, __) -> ArticleRepository.map(rs));
    }

    @Transactional
    @NonNull
    public Optional<ArticleEntity> findById(@NonNull final Long id) {
        LOGGER.debug("execute SQL: {}; id = {}", SQL_FIND_ARTICLE_BY_ID, id);
        final ArticleEntity result = this.jdbcTemplate.query(SQL_FIND_ARTICLE_BY_ID, Map.of("id", id), ArticleRepository.getResultSetExtractor());
        if (result == null) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Transactional
    @NonNull
    public List<ArticleEntity> findByAuthor(@NonNull final String author) {
        LOGGER.debug("execute SQL: {}; author = {}", SQL_FIND_ARTICLE_BY_AUTHOR, author);
        return this.jdbcTemplate.query(SQL_FIND_ARTICLE_BY_AUTHOR, Map.of("author", author), (rs, __) -> ArticleRepository.map(rs));
    }

    @Transactional
    public ArticleEntity save(final ArticleDraft articleDraft) {
        final Long insertedKey = this.jdbcTemplate.execute( //
            SQL_SAVE_ARTICLE //
            , Map.of( //
                "title", articleDraft.getTitle() //
                , "author", articleDraft.getAuthor() //
                , "body", articleDraft.getBody() //
            ) //
            , ArticleRepository.handlerAfterArticleInsert());
        return new ArticleEntity( //
            insertedKey //
            , articleDraft.getTitle() //
            , articleDraft.getAuthor() //
            , articleDraft.getBody() //
        );
    }

    @NonNull
    private static PreparedStatementCallback<Long> handlerAfterArticleInsert() {
        return (ps) -> {
            final boolean resultType = ps.execute();
            if (!resultType) {
                // expected resultType == true because we add RETURNING clause
                throw new InvalidDataAccessApiUsageException( //
                    "unexpected execution result for SQL: \"" + SQL_SAVE_ARTICLE + "\". expected PreparedStatement#execute() return true, but returned false." //
                );
            }
            final ResultSet result = ps.getResultSet();
            if (!result.next()) {
                throw new DataRetrievalFailureException("expected result not found.");
            }
            return result.getLong("id");
        };
    }

    @Nullable
    private static ArticleEntity map(@NonNull final ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new ArticleEntity( //
                rs.getLong("id") //
                , rs.getString("title") //
                , rs.getString("author") //
                , rs.getString("body") //
            );
        }
        return null;
    }

    // ResultSetExtractorであることを明示しないと NamedParameterJdcbTemplate#query() メソッドが
    // 次のどちらなのかコンパイラが決定できないためこの関数を使うことで明示する。
    // - NamedParameterJdcbTemplate#query(String, Map, ResultSetExtractor)
    // - NamedParameterJdcbTemplate#query(String, Map, RowCallbackHandler)
    // ResultSetExtractor と RowCallbackHandler はlambda関数で書くと区別できない
    @NonNull
    private static ResultSetExtractor<ArticleEntity> getResultSetExtractor() {
        return (rs) -> ArticleRepository.map(rs);
    }

}
