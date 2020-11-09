package com.cnc.qr.common.dto;

import lombok.Data;

@Data
public class UnicastDto {

    /**
     * プッシュ端末のトークンID.
     */
    private String tokenId;

    /**
     * プッシュ端末のメッセージ内容.
     */
    private String message;

}
