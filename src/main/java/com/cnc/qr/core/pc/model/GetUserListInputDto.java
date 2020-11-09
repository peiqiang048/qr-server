package com.cnc.qr.core.pc.model;

import com.cnc.qr.common.constants.RegexConstants;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * ユーザ一覧取得条件.
 */
@Data
public class GetUserListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ビジネスID.
     */
    private String businessId;

    /**
     * 店舗ID.
     */
    private String storeId;
}
