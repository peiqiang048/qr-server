package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.AreaType;
import com.cnc.qr.common.repository.MAreaRepository;
import com.cnc.qr.core.pc.model.GetAreaInfoDto;
import com.cnc.qr.core.pc.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.pc.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.pc.service.AreaInfoService;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * エリアサービス実装クラス.
 */
@Service
public class PCAreaInfoServiceImpl implements AreaInfoService {

    /**
     * 配達区域マスタリポジトリ.
     */
    @Autowired
    private MAreaRepository areaRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 指定区域取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    @Override
    public GetSelectedAreaListOutputDto getSelectedAreaList(GetSelectedAreaListInputDto inputDto) {

        // 指定区域取得
        GetSelectedAreaListOutputDto outDto = new GetSelectedAreaListOutputDto();

        List<Map<String, Object>> areaList = new ArrayList<>();

        // 市区町村一覧取得
        if (AreaType.PREFECTURE.getCode().equals(inputDto.getAreaType())) {
            areaList = areaRepository.getCityListByChangePrefecture(inputDto.getLanguages(),
                inputDto.getPrefectureId());
            // 町域番地一覧取得
        } else {
            areaList = areaRepository.getBlockListByChangeCity(inputDto.getLanguages(),
                inputDto.getPrefectureId(), inputDto.getCityId());
        }

        // 指定区域を変換する
        List<GetAreaInfoDto> areaInfoDtoList = new ArrayList<>();
        areaList.forEach(stringObjectMap -> areaInfoDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetAreaInfoDto.class)));

        outDto.setAreaList(areaInfoDtoList);

        // インプット情報をDTOにセットする
        return outDto;
    }
}
