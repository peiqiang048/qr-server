package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MOptionType;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MOptionRepository;
import com.cnc.qr.common.repository.MOptionTypeRepository;
import com.cnc.qr.common.repository.RItemOptionRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.ChangeOptionTypeSortOrderInputDto;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import com.cnc.qr.core.pc.model.DeleteOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeList;
import com.cnc.qr.core.pc.model.GetOptionTypeListInputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeOutputDto;
import com.cnc.qr.core.pc.model.OptionTypeCdListDto;
import com.cnc.qr.core.pc.model.RegisterOptionTypeInputDto;
import com.cnc.qr.core.pc.service.OptionTypeService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * オプション種類情報取得サービス実装クラス.
 */
@Service
public class OptionTypeServiceImpl implements OptionTypeService {


    /**
     * オプション種類マスタリポジトリ.
     */
    @Autowired
    private MOptionTypeRepository optionTypeRepository;

    /**
     * 商品オプション種類関連リポジトリ.
     */
    @Autowired
    private RItemOptionRepository itemOptionRepository;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * オプションマスタリポジトリ.
     */
    @Autowired
    private MOptionRepository optionRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * オプション種類情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類情報
     */
    @Override
    public GetOptionTypeListOutputDto getOptionTypeList(GetOptionTypeListInputDto inputDto) {

        // オプション種類情報取得
        List<Map<String, Object>> optionTypeListMap = optionTypeRepository
            .getOptionTypeLists(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_OPTION_TYPE_CLASSIFICATION, inputDto.getLanguages());

        List<GetOptionTypeList> optionTypeList = new ArrayList<>();

        optionTypeListMap.forEach(stringObjectMap -> optionTypeList.add(JSONObject
            .parseObject(JSONObject.toJSONString(stringObjectMap), GetOptionTypeList.class)));

        GetOptionTypeListOutputDto output = new GetOptionTypeListOutputDto();

        output.setOptionTypeList(optionTypeList);

        return output;
    }

    /**
     * オプション種類明細情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類情報
     */
    @Override
    public GetOptionTypeOutputDto getOptionTypeInfo(GetOptionTypeInputDto inputDto) {

        // オプション種類情報取得
        GetOptionTypeOutputDto optionTypeInfo =
            optionTypeRepository
                .getOptionTypeInfo(inputDto.getStoreId(), inputDto.getOptionTypeCd());

        // 検索結果0件の場合
        if (null == optionTypeInfo) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.072", (Object) null));
        }

        return optionTypeInfo;
    }

    /**
     * オプション種類区分取得.
     *
     * @param inputDto 取得条件
     * @return オプション種類区分情報
     */
    @Override
    public List<ClassificationInfoDto> getClassificationList(GetOptionTypeInputDto inputDto) {

        // オプション種類区分取得
        List<ClassificationInfoDto> classificationList = codeRepository.getClassificationList(
            inputDto.getStoreId(), CommonConstants.CODE_GROUP_OPTION_TYPE_CLASSIFICATION);

        return classificationList;
    }

    /**
     * オプション種類情報編集.
     *
     * @param inputDto オプション種類情報
     */
    @Override
    @Transactional
    public void registerOptionType(RegisterOptionTypeInputDto inputDto) {

        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 編集の場合
        if (null != inputDto.getOptionTypeCd() && StringUtils
            .isNotBlank(inputDto.getOptionTypeCd())) {

            // オプション種類情報取得
            MOptionType optionTypeData = optionTypeRepository
                .findByOptionTypeCdForLock(inputDto.getStoreId(), inputDto.getOptionTypeCd());

            // 検索結果0件の場合
            if (optionTypeData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.072", (Object) null));
            }

            // オプション種類情報編集
            optionTypeRepository.updateOptionType(inputDto.getStoreId(), inputDto.getOptionTypeCd(),
                inputDto.getOptionTypeName(), inputDto.getClassification(), userOperCd, nowTime);

            // 登録の場合
        } else {

            Integer maxOptionTypeCd;

            // 最大optionTypeCd取得
            Integer maxCd = optionTypeRepository.getMaxOptionTypeCode(inputDto.getStoreId());

            if (null == maxCd) {

                maxOptionTypeCd = 1;
            } else {

                maxOptionTypeCd = maxCd + 1;
            }

            Integer sortOrder;

            // 最大maxSortOrder取得
            Integer maxSort = optionTypeRepository.getMaxOptionTypeSort(inputDto.getStoreId());

            if (null == maxSort) {

                sortOrder = 1;
            } else {

                sortOrder = maxSort + 1;
            }

            // オプション種類情報作成
            optionTypeRepository.registOptionType(inputDto.getStoreId(),
                String.format("%04d", maxOptionTypeCd).toString(), inputDto.getOptionTypeName(),
                sortOrder, inputDto.getClassification(), userOperCd, nowTime);
        }
    }

    /**
     * オプション種類情報削除.
     *
     * @param inputDto オプション種類情報
     */
    @Override
    @Transactional
    public void deleteOptionType(DeleteOptionTypeInputDto inputDto) {

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // カテゴリーID取得
        List<String> optionTypeCdList = new ArrayList<>();
        for (OptionTypeCdListDto optionTypeCd : inputDto.getOptionTypeList()) {
            optionTypeCdList.add(optionTypeCd.getOptionTypeCd());
        }

        Integer hasOptionCount = optionRepository.getOptionCount(
            inputDto.getStoreId(), optionTypeCdList);

        if (hasOptionCount != 0) {

            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.073", (Object) null));
        }

        // オプション種類数据删除
        optionTypeRepository.updateDelFlagByOptionTypeCd(inputDto.getStoreId(), optionTypeCdList,
            userOperCd, dateTime);

        // 商品オプション種類関連情報削除
        itemOptionRepository.deleteByOptionTypeList(inputDto.getStoreId(), optionTypeCdList);
    }

    /**
     * オプション種類順番編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeOptionTypeSortOrder(ChangeOptionTypeSortOrderInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 登録更新者
            String userOperCd = CommonConstants.OPER_CD_STORE_PC;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            List<OptionTypeCdListDto> optionTypeSortOrderList = inputDto
                .getOptionTypeSortOrderList();

            for (int i = 0; i < optionTypeSortOrderList.size(); i++) {

                // オプション種類順番編集サービス
                optionTypeRepository.updateOptionTypeSort(inputDto.getStoreId(),
                    optionTypeSortOrderList.get(i).getOptionTypeCd(), i + 1, userOperCd, dateTime);
            }
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.074", (Object) null), ex);
        }
    }
}
