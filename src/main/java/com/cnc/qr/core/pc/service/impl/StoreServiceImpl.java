package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.pc.constants.PCConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.MediumType;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.StaffCheckFlag;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MControl;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.MStoreMedium;
import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MControlRepository;
import com.cnc.qr.common.repository.MLicenseRepository;
import com.cnc.qr.common.repository.MStoreMediumRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.RRoleMenuRepository;
import com.cnc.qr.common.repository.RUserRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.UploadFileDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.core.pc.model.ChangeStoreInputDto;
import com.cnc.qr.core.pc.model.ChildrenMenuInfoDto;
import com.cnc.qr.core.pc.model.EditStoreMediaDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoInputDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoInputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInputDto;
import com.cnc.qr.core.pc.model.GetStoreListInputDto;
import com.cnc.qr.core.pc.model.GetStoreListOutputDto;
import com.cnc.qr.core.pc.model.GetStoreOutputDto;
import com.cnc.qr.core.pc.model.LanguageInfoDto;
import com.cnc.qr.core.pc.model.MediaUseDto;
import com.cnc.qr.core.pc.model.MenuInfoDto;
import com.cnc.qr.core.pc.model.PicUrlDto;
import com.cnc.qr.core.pc.model.StoreDto;
import com.cnc.qr.core.pc.model.StoreMediaDto;
import com.cnc.qr.core.pc.model.UploadFileInputDto;
import com.cnc.qr.core.pc.model.UploadFileOutputDto;
import com.cnc.qr.core.pc.model.UserMenuInfoDataDto;
import com.cnc.qr.core.pc.service.StoreService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店舗サービス実装クラス.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    /**
     * 店舗リポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * コトロルリポジトリ.
     */
    @Autowired
    private MControlRepository controlRepository;

    /**
     * コードリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * ライセンスマスタリポジトリ.
     */
    @Autowired
    private MLicenseRepository licenseRepository;

    /**
     * 店舗媒体マスタリポジトリ.
     */
    @Autowired
    private MStoreMediumRepository storeMediumRepository;

    /**
     * 役割メニュー関連テーブルリポジトリ.
     */
    @Autowired
    private RRoleMenuRepository roleMenuRepository;

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    private RUserRepository userRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 店舗一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetStoreListOutputDto getStoreList(GetStoreListInputDto inputDto) {

        List<String> storeIdList = new ArrayList<String>();

        for (int i = 0; i < inputDto.getStoreList().size(); i++) {
            storeIdList.add(inputDto.getStoreList().get(i).getStoreId());
        }

        // 店舗一覧情報を取得する
        List<Map<String, Object>> storeInfoList = storeRepository
            .findStoreList(inputDto.getLoginId(), CommonConstants.CODE_GROUP_PAYMENT, storeIdList);

        // 店舗一覧情報を変換する
        List<StoreDto> storeList = new ArrayList<>();
        storeInfoList.forEach(stringObjectMap -> storeList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), StoreDto.class)));

        // 店舗一覧情報を作成する
        GetStoreListOutputDto outputDto = new GetStoreListOutputDto();

        // 店舗情報を設定する
        outputDto.setStoreList(storeList);

        return outputDto;
    }

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetStoreOutputDto getStore(GetStoreInputDto inputDto) {
        // 店舗情報を取得する
        Map<String, Object> storeInfo = storeRepository
            .findStoreInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT);

        // 店舗情報を設定する
        GetStoreOutputDto outputDto = JSONObject
            .parseObject(JSONObject.toJSONString(storeInfo), GetStoreOutputDto.class);

        // 店舗媒体情報を取得する
        List<Map<String, Object>> mediumList = storeMediumRepository
            .findMediaInfo(inputDto.getStoreId());

        // 店舗媒体情報を変換する
        List<StoreMediaDto> storeMediaDtoList = new ArrayList<>();
        mediumList.forEach(stringObjectMap -> storeMediaDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), StoreMediaDto.class)));

        // 媒体用途情報を取得する
        List<Map<String, Object>> mediumUseList = codeRepository
            .findMediaUseInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_MEDIA_USE);

        // 媒体用途情報を変換する
        List<MediaUseDto> mediaUseDtoList = new ArrayList<>();
        mediumUseList.forEach(stringObjectMap -> mediaUseDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), MediaUseDto.class)));

        // 店舗媒体情報を設定する
        outputDto.setStoreMediaList(storeMediaDtoList);
        // 媒体用途情報を設定する
        outputDto.setUseList(mediaUseDtoList);
        // 支払区分
        List<PaymentMethodDto> paymentTypeList = new ArrayList<>();
        for (PaymentType paymentType : PaymentType.values()) {
            PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
            paymentMethodDto.setPaymentCode(paymentType.getCode());
            paymentMethodDto.setPaymentName(paymentType.getValue());
            paymentTypeList.add(paymentMethodDto);
        }
        outputDto.setPaymentTypeList(paymentTypeList);
        // スマホ注文確認要否
        List<PaymentMethodDto> staffCheckList = new ArrayList<>();
        for (StaffCheckFlag paymentType : StaffCheckFlag.values()) {
            PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
            paymentMethodDto.setPaymentCode(paymentType.getCode());
            paymentMethodDto.setPaymentName(paymentType.getValue());
            staffCheckList.add(paymentMethodDto);
        }
        outputDto.setStaffCheckList(staffCheckList);

        return outputDto;
    }

    /**
     * 店舗情報編集.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void changeStore(ChangeStoreInputDto inputDto) {

        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 指定店舗情報をロックする
        MStore store = storeRepository.findByStoreIdForLock(inputDto.getStoreId());

        // 店舗がNULLである場合
        if (store == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }
        String openStart = inputDto.getOpenStoreTime();
        if (openStart.length() > 5) {
            Instant instant = Instant.parse(inputDto.getOpenStoreTime());
            ZonedDateTime openStoreTime = ZonedDateTime
                .ofInstant(instant, ZoneId.of(CommonConstants.TIMEZONE_TOKYO));
            openStart = DateUtil.getZonedDateString(openStoreTime, "HH:mm");
        }
        String closeStart = inputDto.getCloseStoreTime();
        if (closeStart.length() > 5) {
            Instant instant = Instant.parse(inputDto.getCloseStoreTime());
            ZonedDateTime closeStartTime = ZonedDateTime
                .ofInstant(instant, ZoneId.of(CommonConstants.TIMEZONE_TOKYO));
            closeStart = DateUtil.getZonedDateString(closeStartTime, "HH:mm");
        }
        String orderEnd = inputDto.getOrderEndTime();
        if (orderEnd.length() > 5) {
            Instant instant = Instant.parse(inputDto.getOrderEndTime());
            ZonedDateTime orderEndTime = ZonedDateTime
                .ofInstant(instant, ZoneId.of(CommonConstants.TIMEZONE_TOKYO));
            orderEnd = DateUtil.getZonedDateString(orderEndTime, "HH:mm");
        }
        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // 店舗情報を更新する
        storeRepository
            .updateStore(inputDto.getStoreName(), inputDto.getStoreNameKatakana(),
                inputDto.getStoreIntrodution(), inputDto.getStoreAddress(), inputDto.getTel(),
                inputDto.getPostNumber(), inputDto.getFax(), openStart,
                closeStart, orderEnd, inputDto.getStaffCheckFlag(), inputDto.getCourseBuffetCheck(),
                inputDto.getDefaultUseTime(),
                userOperCd, nowDateTime, inputDto.getStoreId());

        // コントロール情報をロックする
        MControl control = controlRepository
            .findByStoreIdAndControlTypeForLock(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAYMENT);

        // コントロールがNULLである場合
        if (control == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }

        // 店舗支払方式を更新する
        controlRepository
            .updateStorePaymentMethod(CommonConstants.CODE_GROUP_PAYMENT,
                inputDto.getPaymentTerminal(), userOperCd, nowDateTime,
                inputDto.getStoreId());

        // 媒体IDリストを編集する
        List<Integer> mediaIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(inputDto.getDelStoreMediaList())) {
            for (StoreMediaDto storeMedia : inputDto.getDelStoreMediaList()) {
                if (StringUtils.isNotEmpty(storeMedia.getMediaId())) {
                    mediaIdList.add(Integer.parseInt(storeMedia.getMediaId()));
                }
            }
        }

        if (CollectionUtils.isNotEmpty(inputDto.getStoreMediaList())) {
            for (EditStoreMediaDto storeMedia : inputDto.getStoreMediaList()) {
                if (CollectionUtils.isNotEmpty(storeMedia.getStoreMediaList())) {
                    storeMedia.getStoreMediaList().forEach(storeMediaDto -> {
                        if (StringUtils.isNotEmpty(storeMediaDto.getMediaId())) {
                            mediaIdList.add(Integer.parseInt(storeMediaDto.getMediaId()));
                        }
                    });
                }
            }
        }

        // 店舗媒体を削除する
        if (CollectionUtils.isNotEmpty(mediaIdList)) {
            storeMediumRepository.deleteStoreMediums(inputDto.getStoreId(), mediaIdList);
        }

        // 店舗媒体を登録する
        for (EditStoreMediaDto editStoreMedia : inputDto.getStoreMediaList()) {
            if (CollectionUtils.isNotEmpty(editStoreMedia.getStoreMediaList())) {
                int i = 1;
                for (StoreMediaDto storeMedia : editStoreMedia.getStoreMediaList()) {
                    // 媒体IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                    getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqNoInputDto.setTableName("m_store_medium"); // テーブル名
                    getSeqNoInputDto.setItem("medium_id"); // 項目
                    getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
                    GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);
                    MStoreMedium storeMedium = new MStoreMedium();
                    storeMedium.setStoreId(inputDto.getStoreId());
                    storeMedium.setMediumId(getSeqNo.getSeqNo());
                    storeMedium.setMediumType(storeMedia.getMediaType());
                    storeMedium.setSortOrder(i + 1);
                    storeMedium.setTerminalDistinction(storeMedia.getMediaUse());
                    storeMedium.setImagePath(storeMedia.getMediaUrl());
                    storeMedium.setDelFlag(Flag.OFF.getCode());
                    storeMedium.insOperCd(userOperCd);
                    storeMedium.insDateTime(nowDateTime);
                    storeMedium.updOperCd(userOperCd);
                    storeMedium.updDateTime(nowDateTime);
                    storeMedium.setVersion(0);
                    storeMediumRepository.save(storeMedium);
                    i++;
                }
            }
        }
    }

    /**
     * ホームページ情報取得.
     *
     * @param inputDto 取得条件
     * @return ホームページ情報
     */
    @Override
    public GetHomePageInfoOutputDto getHomePageInfo(GetHomePageInfoInputDto inputDto) {

        // 店舗情報取得
        MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (storeInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // ユーザ情報取得
        List<RUser> userInfoList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(storeInfo.getBusinessId(),
                inputDto.getLoginId(),
                Flag.OFF.getCode());

        // 検索結果0件の場合
        if (userInfoList.size() == 0) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.054", (Object) null));
        }

        // 店舗媒体情報取得
        List<LanguageInfoDto> storeLanguageList = licenseRepository
            .findStoreLanguageByStoreId(inputDto.getStoreId(), CommonConstants.CODE_GROUP_LANGUAGE);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storeLanguageList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetHomePageInfoOutputDto outDto = new GetHomePageInfoOutputDto();
        // 店舗情報設定
        outDto.setStoreId(storeInfo.getStoreId());
        // 店舗名
        outDto.setStoreName(storeInfo.getStoreName());
        // 店舗名
        outDto.setUserName(userInfoList.get(0).getUserName());
        // 店舗Logo取得
        List<MStoreMedium> storeLogoList =
            storeMediumRepository
                .findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
                    inputDto.getStoreId(), MediumType.LOGO.getCode(),
                    TerminalDistinction.ADMIN.getCode(), Flag.OFF.getCode());
        // 店舗Logo Url
        if (CollectionUtils.isNotEmpty(storeLogoList)) {
            outDto.setStoreLogoUrl(storeLogoList.get(0).getImagePath());
        }
        // 店舗言語
        outDto.setLanguages(storeLanguageList);
        // ユーザメニュー情報取得
        List<UserMenuInfoDataDto> userMenuInfo = roleMenuRepository
            .getMenuInfoByUserId(storeInfo.getBusinessId(), inputDto.getStoreId(),
                inputDto.getLoginId());
        // メニュー情報
        Map<Object, List<UserMenuInfoDataDto>> grpByParentMenuId = userMenuInfo.stream().collect(
            Collectors.groupingBy(
                userMenuInfoDataDto -> Optional.ofNullable(userMenuInfoDataDto.getParentMenuId())));

        // メニュー情報設定
        List<MenuInfoDto> menuList = new ArrayList<>();
        grpByParentMenuId.forEach((o, userMenuInfoDataDtos) -> {
            MenuInfoDto menuInfo = beanMapper.map(userMenuInfoDataDtos.get(0), MenuInfoDto.class);
            menuInfo.setParentMenuName(
                JSONObject.parseObject(userMenuInfoDataDtos.get(0).getParentMenuName())
                    .getString(inputDto.getLanguages()));
            List<ChildrenMenuInfoDto> childrenList = new ArrayList<>();
            userMenuInfoDataDtos.forEach(userMenuInfoDataDto -> {
                ChildrenMenuInfoDto childrenMenuInfo = beanMapper
                    .map(userMenuInfoDataDto, ChildrenMenuInfoDto.class);
                childrenMenuInfo.setChildrenMenuName(
                    JSONObject.parseObject(userMenuInfoDataDto.getChildrenMenuName())
                        .getString(inputDto.getLanguages()));
                childrenList.add(childrenMenuInfo);
            });
            menuInfo.setChildrenList(childrenList);
            menuList.add(menuInfo);
        });
        outDto.setMenuList(menuList);

        // 店舗画像設定
        List<PicUrlDto> picList = new ArrayList<>();

        // 店舗媒体情報取得
        List<MStoreMedium> storeMediumList =
            storeMediumRepository
                .findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
                    inputDto.getStoreId(), MediumType.IMAGE.getCode(),
                    TerminalDistinction.ADMIN.getCode(), Flag.OFF.getCode());
        storeMediumList.forEach(storeMedium -> {
            PicUrlDto picUrlDto = new PicUrlDto();
            picUrlDto.setPicUrl(storeMedium.getImagePath());
            picList.add(picUrlDto);
        });
        outDto.setPicList(picList);

        return outDto;
    }

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗情報
     */
    @Override
    public GetStoreInfoOutputDto getStoreInfo(GetStoreInfoInputDto inputDto) {

        // 店舗言語情報取得
        List<LanguageInfoDto> storeLanguageList = licenseRepository
            .findStoreLanguageByStoreId(inputDto.getStoreId(), CommonConstants.CODE_GROUP_LANGUAGE);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storeLanguageList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetStoreInfoOutputDto outDto = new GetStoreInfoOutputDto();

        // 店舗言語
        outDto.setLanguages(storeLanguageList);

        // 当前店铺最大分类层数
        outDto.setMaxGradation(PCConstants.GRADATION.toString());

        return outDto;
    }

    /**
     * ファイルアップロード.
     *
     * @param inputDto 取得条件
     * @return アップロード結果
     */
    @Override
    public UploadFileOutputDto uploadFile(UploadFileInputDto inputDto) {

        // ファイルをアップロードする
        UploadFileDto uploadFileData = itemInfoSharedService
            .uploadFiles(inputDto.getStoreId(), inputDto.getUseType(), inputDto.getFile());

        // 結果DTO初期化
        UploadFileOutputDto outDto = new UploadFileOutputDto();
        outDto.setFilePath(uploadFileData.getFilePath());
        outDto.setFileSmallPath(uploadFileData.getFileSmallPath());

        return outDto;
    }
}
