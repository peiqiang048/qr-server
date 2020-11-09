package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 役割一覧取得結果.
 */
@Data
public class GetRoleListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 役割リスト.
     */
    private Page<UserRoleInfoDto> roleList;

    /**
     * 数据条数.
     */
    private Long totalCount;

}
