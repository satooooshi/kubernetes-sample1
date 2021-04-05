CREATE TABLE "daily_total_entry" (
    "id" bigserial NOT NULL,
    "date" date NOT NULL,
    "rank" int NOT NULL,
    "article_id" bigint NOT NULL,
    "total_access" bigint NOT NULL,
    CONSTRAINT "daily_total_entry_id_pkc" PRIMARY KEY ("id"),
    CONSTRAINT "daily_total_entry_date_rank_ukc" UNIQUE ("date", "rank"),
    CONSTRAINT "daily_total_entry_date_article_id_ukc" UNIQUE ("date", "article_id") DEFERRABLE INITIALLY DEFERRED
);
CREATE TABLE "daily_unique_entry" (
    "id" bigserial NOT NULL,
    "date" date NOT NULL,
    "rank" int NOT NULL,
    "article_id" bigint NOT NULL,
    "unique_access" bigint NOT NULL,
    CONSTRAINT "daily_unique_entry_id_pkc" PRIMARY KEY ("id"),
    CONSTRAINT "daily_unique_entry_date_rank_ukc" UNIQUE ("date", "rank"),
    CONSTRAINT "daily_unique_entry_date_article_id_ukc" UNIQUE ("date", "article_id") DEFERRABLE INITIALLY DEFERRED
);
