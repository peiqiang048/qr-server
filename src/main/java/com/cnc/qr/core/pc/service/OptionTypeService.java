package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeOptionTypeSortOrderInputDto;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import com.cnc.qr.core.pc.model.DeleteOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeListInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeOutputDto;
import com.cnc.qr.core.pc.model.RegisterOptionTypeInputDto;
import java.util.List;

/**
 * オプション種類情報取得サービス.
 */
public interface OptionTypeService {

    /**
     * オプション種類情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類情報
     */
    GetOptionTypeListOutputDto getOptionTypeList(GetOptionTypeListInputDto inputDto);

    /**
     * オプション種類明細情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類明細情報
     */
    GetOptionTypeOutputDto getOptionTypeInfo(GetOptionTypeInputDto inputDto);

    /**
     * オプション種類区分情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類区分情報
     */
    List<ClassificationInfoDto> getClassificationList(GetOptionTypeInputDto inputDto);

    /**
     * オプション種類情報編集.
     *
     * @param inputDto 取得条件
     */
    void registerOptionType(RegisterOptionTypeInputDto inputDto);

    /**
     * オプション種類情報削除.
     *
     * @param inputDto 取得条件
     */
    void deleteOptionType(DeleteOptionTypeInputDto inputDto);

    /**
     * オプション種類順番編集.
     *
     * @param inputDto 取得条件
     */
    void changeOptionTypeSortOrder(ChangeOptionTypeSortOrderInputDto inputDto);
}
