package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 配せき情報取得検索結果.
 */
@Data
public class AssignationTableOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 配せきリスト情報.
     */
    private List<AssignationTableDto> assignationTableList;


}
