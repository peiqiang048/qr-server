package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 予約一覧情報取得検索結果.
 */
@Data
public class GetReservateListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 予約総件数.
     */
    private long reservateCount;

    /**
     * 予約一覧情報.
     */
    private Page<ReservateInfoDto> reservateList;
}
