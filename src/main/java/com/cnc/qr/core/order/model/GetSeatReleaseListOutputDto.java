package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 席解除一覧取得結果.
 */
@Data
public class GetSeatReleaseListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 席解除リスト.
     */
    private List<SeatReleaseDto> seatReleaseList;
}
