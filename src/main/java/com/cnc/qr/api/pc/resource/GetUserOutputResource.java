package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import com.cnc.qr.core.pc.model.RoleInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetUserOutputResource extends CommonOutputResource {

    /**
     * ログインID.
     */
    private String loginId;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * ユーザ区分.
     */
    private String classification;

    /**
     * 選択した役割.
     */
    private List<RoleInfoDto> checkedRoleList;

    /**
     * 区分リスト.
     */
    private List<ClassificationInfoDto> classificationList;

    /**
     * 役割リスト.
     */
    private List<RoleInfoDto> roleList;
}
