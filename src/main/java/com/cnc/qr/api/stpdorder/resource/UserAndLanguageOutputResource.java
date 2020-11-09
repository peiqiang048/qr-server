package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.StoreLanguageDto;
import com.cnc.qr.core.order.model.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class UserAndLanguageOutputResource extends CommonOutputResource {

    /**
     * ユーザリスト.
     */
    private List<UserDto> userList;


    /**
     * 言語リスト.
     */
    private List<StoreLanguageDto> languageList;


}
