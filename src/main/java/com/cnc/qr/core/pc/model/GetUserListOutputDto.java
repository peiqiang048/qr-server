package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * ユーザ一覧取得結果.
 */
@Data
public class GetUserListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザリスト.
     */
    private Page<UserInfoDto> userList;
    
    /**
     * 数据条数.
     */
    private Long totalCount;

}
