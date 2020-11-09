package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MDeliveryArea;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MAreaRepository;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MDeliveryAreaRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.AreaListDto;
import com.cnc.qr.core.pc.model.AreaListOutputDto;
import com.cnc.qr.core.pc.model.BlockListDto;
import com.cnc.qr.core.pc.model.CateringPriceTypeListDto;
import com.cnc.qr.core.pc.model.CityListDto;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingInputDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingOutputDto;
import com.cnc.qr.core.pc.model.PrefectureListDto;
import com.cnc.qr.core.pc.model.RegistDelivertSettingInputDto;
import com.cnc.qr.core.pc.service.DeliveryService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出前設定取得サービス実装クラス.
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 区域マスタリポジトリ.
     */
    @Autowired
    private MAreaRepository areaRepository;

    /**
     * 配達区域マスタリポジトリ.
     */
    @Autowired
    private MDeliveryAreaRepository deliveryAreaRepository;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * 出前設定取得.
     *
     * @param inputDto 取得条件
     * @return 出前設定情報
     */
    @Override
    public GetDeliverySettingOutputDto getDeliverySetting(GetDeliverySettingInputDto inputDto) {

        Map<String, Object> deliverySettingMap = storeRepository
            .getDeliverySetting(inputDto.getStoreId());

        // 出前設定取得情報を設定する
        GetDeliverySettingOutputDto outputDto = JSONObject
            .parseObject(JSONObject.toJSONString(deliverySettingMap),
                GetDeliverySettingOutputDto.class);

        return outputDto;

    }

    /**
     * 都道府県リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 都道府県情報
     */
    @Override
    public List<PrefectureListDto> getPrefectureList(GetDeliverySettingInputDto inputDto) {

        // 都道府県リスト取得
        List<Map<String, Object>> prefectureList = areaRepository
            .getPrefectureList(inputDto.getLanguages());

        List<PrefectureListDto> prefectureDtoList = new ArrayList<>();

        prefectureList.forEach(stringObjectMap -> prefectureDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), PrefectureListDto.class)));

        return prefectureDtoList;
    }

    /**
     * 市区町村リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 市区町村情報
     */
    @Override
    public List<CityListDto> getCityList(GetDeliverySettingInputDto inputDto) {

        // 市区町村リスト取得
        List<Map<String, Object>> cityList = areaRepository
            .getCityListByPrefecture(inputDto.getLanguages(), inputDto.getPrefectureId());

        List<CityListDto> cityDtoList = new ArrayList<>();

        cityList.forEach(stringObjectMap -> cityDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), CityListDto.class)));

        return cityDtoList;
    }

    /**
     * 町域番地リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 都道府県情報
     */
    @Override
    public List<BlockListDto> getBlockList(GetDeliverySettingInputDto inputDto) {

        // 町域番地リスト取得
        List<Map<String, Object>> blockList = areaRepository
            .getBlockListByPrefecture(inputDto.getLanguages(), inputDto.getPrefectureId(),
                inputDto.getCityId());

        List<BlockListDto> blockDtoList = new ArrayList<>();

        blockList.forEach(stringObjectMap -> blockDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), BlockListDto.class)));

        return blockDtoList;
    }

    /**
     * エリアリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    @Override
    public List<AreaListDto> getAreaList(GetDeliverySettingInputDto inputDto) {

        // エリアリスト取得
        List<Map<String, Object>> areaList = deliveryAreaRepository.getAreaList(
            inputDto.getStoreId(), inputDto.getLanguages());

        List<AreaListDto> areaDtoList = new ArrayList<>();

        areaList.forEach(stringObjectMap -> areaDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), AreaListDto.class)));

        return areaDtoList;
    }

    /**
     * 出前区分リスト取得.
     *
     * @param inputDto 取得条件
     * @return 出前区分リスト
     */
    @Override
    public List<DeliveryFlagListDto> getDeliveryFlagList(GetDeliverySettingInputDto inputDto) {

        // 出前区分情報を取得する
        List<Map<String, Object>> deliveryFlagList = codeRepository
            .findDeliveryFlagListInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_FLAG);

        // 出前区分情報を変換する
        List<DeliveryFlagListDto> deliveryFlagDtoList = new ArrayList<>();
        deliveryFlagList.forEach(stringObjectMap -> deliveryFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryFlagListDto.class)));

        return deliveryFlagDtoList;
    }

    /**
     * 出前仕方リスト取得.
     *
     * @param inputDto 取得条件
     * @return 出前仕方リスト
     */
    @Override
    public List<DeliveryTypeFlagListDto> getDeliveryTypeFlagList(
        GetDeliverySettingInputDto inputDto) {

        // 出前仕方情報を取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findCateringTypeListInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_SET_DELIVERY_TYPE_FLAG);

        // 出前仕方情報を変換する
        List<DeliveryTypeFlagListDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    DeliveryTypeFlagListDto.class)));

        return deliveryTypeFlagDtoList;
    }

    /**
     * 配達料区分リスト取得.
     *
     * @param inputDto 取得条件
     * @return 配達料区分リスト
     */
    @Override
    public List<CateringPriceTypeListDto> getCateringPriceTypeList(
        GetDeliverySettingInputDto inputDto) {

        // 配達料区分情報を取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findCateringPriceTypeListInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_CATERING_PRICE_TYPE);

        // 配達料区分情報を変換する
        List<CateringPriceTypeListDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    CateringPriceTypeListDto.class)));

        return deliveryTypeFlagDtoList;
    }

    /**
     * 出前設定情報更新.
     *
     * @param inputDto 出前設定情報
     */
    @Override
    @Transactional
    public void registDelivertSetting(RegistDelivertSettingInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 指定店舗情報をロックする
            MStore store = storeRepository.findByStoreIdForLock(inputDto.getStoreId());

            // 店舗がNULLである場合
            if (store == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 店舗情報を更新する
            storeRepository
                .updateDeliveryStore(inputDto.getCateringIntervalTime(),
                    inputDto.getTakeoutIntervalTime(),
                    inputDto.getIntervalTime(), inputDto.getDeliveryTypeFlag(),
                    inputDto.getDeliveryFlag(),
                    inputDto.getCateringPriceType(), inputDto.getCateringAmount(),
                    inputDto.getCateringPercent(),
                    userOperCd, dateTime, inputDto.getStoreId());

            deliveryAreaRepository.deleteByStoreId(inputDto.getStoreId());

            // 配達区域情報作成
            List<MDeliveryArea> deliveryAreaList = new ArrayList<>();
            List<AreaListOutputDto> areaList = inputDto.getAreaList();
            for (AreaListOutputDto areaListOutputDto : areaList) {
                MDeliveryArea deliveryArea = new MDeliveryArea();
                deliveryArea.setStoreId(inputDto.getStoreId());                     //　店舗ID
                deliveryArea.setPrefectureId(areaListOutputDto.getPrefectureId());  // 都道府県ID
                deliveryArea.setCityId(areaListOutputDto.getCityId());              // 市区町村ID
                deliveryArea.setBlockId(areaListOutputDto.getBlockId());            // 町域番地ID
                deliveryArea.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                deliveryArea.setInsOperCd(userOperCd);                              // 登録者
                deliveryArea.setInsDateTime(dateTime);                              // 登録日時
                deliveryArea.setUpdOperCd(userOperCd);                              // 更新者
                deliveryArea.setUpdDateTime(dateTime);                              // 更新日時
                deliveryArea.setVersion(0);                                         // バージョン
                deliveryAreaList.add(deliveryArea);
            }
            deliveryAreaRepository.saveAll(deliveryAreaList);
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.055", (Object) null), ex);
        }
    }

    /**
     * 登録更新者ID取得.
     */
    private String getUserOperCd() {
        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        return userOperCd;
    }
}
