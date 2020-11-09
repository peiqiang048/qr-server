package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザ情報削除データ.
 */
@Data
public class DeleteUserInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * ユーザーIDリスト.
     */
    private List<UserIdDto> userList;
}
