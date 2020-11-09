package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeOptionSortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteOptionInputDto;
import com.cnc.qr.core.pc.model.GetOptionInputDto;
import com.cnc.qr.core.pc.model.GetOptionListInputDto;
import com.cnc.qr.core.pc.model.GetOptionListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionOutputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListInputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListOutputDto;
import com.cnc.qr.core.pc.model.RegistOptionInputDto;
import org.springframework.data.domain.Pageable;

/**
 * オプション情報サービス.
 */
public interface OptionService {

    /**
     * オプション一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション一覧情報
     */
    GetOptionListOutputDto getOptionList(GetOptionListInputDto inputDto, Pageable pageable);

    /**
     * オプション情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション情報
     */
    GetOptionOutputDto getOption(GetOptionInputDto inputDto);

    /**
     * オプション情報編集.
     *
     * @param inputDto 取得条件
     */
    void registOption(RegistOptionInputDto inputDto);

    /**
     * オプション情報削除.
     *
     * @param inputDto オプション情報
     */
    void deleteOption(DeleteOptionInputDto inputDto);

    /**
     * オプション順番情報編集.
     *
     * @param inputDto 取得条件
     */
    void changeOptionSortOrder(ChangeOptionSortOrderInputDto inputDto);

    /**
     * オプション順番一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション一覧情報
     */
    GetOptionSortOrderListOutputDto getOptionSortOrderList(GetOptionSortOrderListInputDto inputDto);
}
