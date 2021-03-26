CREATE TABLE "daily_total_entry" (
    "date" date NOT NULL,
    "rank" int NOT NULL,
    "article_id" bigint NOT NULL,
    "total_access" bigint NOT NULL,
    CONSTRAINT "daily_total_entry_date_rank_pkc" PRIMARY KEY ("date", "rank"),
    CONSTRAINT "daily_total_entry_date_article_id_ukc" UNIQUE ("date", "article_id")
);
CREATE TABLE "daily_unique_entry" (
    "date" date NOT NULL,
    "rank" int NOT NULL,
    "article_id" bigint NOT NULL,
    "unique_access" bigint NOT NULL,
    CONSTRAINT "daily_unique_entry_date_rank_pkc" PRIMARY KEY ("date", "rank"),
    CONSTRAINT "daily_unique_entry_date_article_id_ukc" UNIQUE ("date", "article_id")
);
