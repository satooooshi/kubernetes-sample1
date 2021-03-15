CREATE TABLE accesscount (
    id bigserial NOT NULL
    , article_id bitint NOT NULL
    , uid char(256) NOT NULL
    , access_at timestampz NOT NULL
    , CONSTRAINT accesscount_id_pkc PRIMARY KEY (id)
);
