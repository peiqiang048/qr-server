package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.GetUnitInputDto;
import com.cnc.qr.core.pc.model.GetUnitOutputDto;
import com.cnc.qr.core.pc.model.RegistUnitInputDto;
import com.cnc.qr.core.pc.model.UnitDelInputDto;
import com.cnc.qr.core.pc.model.UnitListInputDto;
import com.cnc.qr.core.pc.model.UnitListOutputDto;
import org.springframework.data.domain.Pageable;

/**
 * 单位サービス.
 */
public interface UnitService {

    /**
     * 单位一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    UnitListOutputDto getUnitList(UnitListInputDto inputDto, Pageable pageable);

    /**
     * 单位情報取得.
     *
     * @param inputDto 取得条件
     */
    GetUnitOutputDto getUnit(GetUnitInputDto inputDto);

    /**
     * 单位保存.
     *
     * @param inputDto 取得条件
     */
    void saveUnit(RegistUnitInputDto inputDto);

    /**
     * 单位削除.
     *
     * @param inputDto 取得条件
     */
    void delUnit(UnitDelInputDto inputDto);

}
