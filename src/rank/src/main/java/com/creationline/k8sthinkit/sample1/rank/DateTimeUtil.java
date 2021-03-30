package com.creationline.k8sthinkit.sample1.rank;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 日付/時刻ユーティリティ
 */
@Component
public class DateTimeUtil {

    /** プロパティ参照 timzone */
    @NonNull
    private final ZoneId systemZone;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param systemZone プロパティ参照 timezone
     */
    public DateTimeUtil( //

        @NonNull //
        final ZoneId systemZone //

    ) {

        this.systemZone = systemZone;

    }

    /**
     * 実行時点の時差を取得する
     * 
     * 時差は一般的にサマータイム等によって時刻によって変化する
     */
    public ZoneOffset currentOffset() {
        return this.systemZone.getRules().getOffset(Instant.now());
    }

    /**
     * {@link LocalDateTime}オブジェクトを時差のある{@link OffsetDateTime}に変換する
     * 
     * @param localdatetime 変換する{@link LocalDateTime}オブジェクト
     * @return 変換された{@link OffsetDateTime}オブジェクト
     */
    public OffsetDateTime toOffsetDateTimeWithSystemZone(@NonNull final LocalDateTime localdatetime) {
        return OffsetDateTime.of(localdatetime, this.currentOffset());
    }

}
