package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * エリア情報取得条件.
 */
@Data
public class GetStoreListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザID.
     */
    private String loginId;
    
    /**
     * 店舗リスト.
     */
    private List<@Valid StoreListDto> storeList;

}
