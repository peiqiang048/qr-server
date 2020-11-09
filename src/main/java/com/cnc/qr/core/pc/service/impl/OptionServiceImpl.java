package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MOption;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MOptionRepository;
import com.cnc.qr.common.repository.MOptionTypeRepository;
import com.cnc.qr.common.repository.RItemOptionRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.ChangeOptionSortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteOptionInputDto;
import com.cnc.qr.core.pc.model.GetOption;
import com.cnc.qr.core.pc.model.GetOptionInputDto;
import com.cnc.qr.core.pc.model.GetOptionList;
import com.cnc.qr.core.pc.model.GetOptionListInputDto;
import com.cnc.qr.core.pc.model.GetOptionListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionOutputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListInputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionType;
import com.cnc.qr.core.pc.model.OptionCodeDto;
import com.cnc.qr.core.pc.model.RegistOptionInputDto;
import com.cnc.qr.core.pc.service.OptionService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * オプション情報サービス実装クラス.
 */
@Service
public class OptionServiceImpl implements OptionService {


    /**
     * オプションマスタリポジトリ.
     */
    @Autowired
    private MOptionRepository optionRepository;

    /**
     * オプション種類マスタリポジトリ.
     */
    @Autowired
    private MOptionTypeRepository optionTypeRepository;

    /**
     * 商品オプション関連リポジトリ.
     */
    @Autowired
    private RItemOptionRepository itemOptionRepository;

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * オプション一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション一覧情報
     */
    @Override
    public GetOptionListOutputDto getOptionList(GetOptionListInputDto inputDto, Pageable pageable) {

        //  オプション一覧情報取得
        Page<Map<String, Object>> optionMap;

        optionMap = optionRepository
            .getOptionList(inputDto.getStoreId(),
                inputDto.getLanguages(),
                StringUtils.isEmpty(inputDto.getOptionTypeCd()) ? StringUtils.EMPTY
                    : inputDto.getOptionTypeCd(),
                StringUtils.isEmpty(inputDto.getOptionName()) ? StringUtils.EMPTY
                    : inputDto.getOptionName(), pageable);

        //  オプション一覧情報を設定する
        List<GetOptionList> optionList = new ArrayList<>();
        optionMap.forEach(stringObjectMap -> optionList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetOptionList.class)));

        GetOptionListOutputDto output = new GetOptionListOutputDto();

        output.setOptionList(new PageImpl<>(optionList,
            optionMap.getPageable(), optionMap.getTotalPages()));

        output.setTotalCount(optionMap.getTotalElements());

        return output;
    }

    /**
     * オプション情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション情報
     */
    @Override
    public GetOptionOutputDto getOption(GetOptionInputDto inputDto) {

        // オプション情報
        GetOption option;

        // オプション種類情報
        List<Map<String, Object>> optionTypeListTmp;

        GetOptionOutputDto output = new GetOptionOutputDto();

        // オプションコードが空白ではない場合
        if (!"".equals(inputDto.getOptionCode())) {
            // オプション情報を取得する
            option = optionRepository.getOption(inputDto.getStoreId(), inputDto.getOptionCode(),
                inputDto.getOptionTypeCd());

            // オプションがNULLではない場合
            if (option != null) {
                output.setOptionName(option.getOptionName());
                output.setOptionTypeCd(option.getOptionTypeCd());
            }
        }

        // オプション種類情報を取得する
        optionTypeListTmp = optionTypeRepository
            .getOptionTypeList(inputDto.getStoreId(), inputDto.getLanguages());

        // オプション種類情報を変換する
        List<GetOptionType> optionTypeList = new ArrayList<>();
        optionTypeListTmp.forEach(stringObjectMap -> optionTypeList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetOptionType.class)));

        // オプション種類リストのサイズが０ではない場合
        if (optionTypeList.size() != 0) {
            output.setOptionTypeList(optionTypeList);
        }

        return output;
    }

    /**
     * オプション情報編集.
     *
     * @param inputDto オプション情報
     */
    @Override
    @Transactional
    public void registOption(RegistOptionInputDto inputDto) {

        // オプションコード
        String optionCode = inputDto.getOptionCode();

        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // オプションコードが空白ではない場合
        if (!"".equals(optionCode)) {
            // オプション情報をロックする
            MOption option = optionRepository
                .findByStoreIdAndOptionTypeCdAndOptionCodeForLock(inputDto.getStoreId(),
                    inputDto.getOptionCode());

            // オプションがNULLである場合
            if (option == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // オプション情報を保存する
            optionRepository.updateOption(inputDto.getStoreId(), inputDto.getOptionCode(),
                inputDto.getOptionName(), inputDto.getOptionTypeCd(), userOperCd, dateTime);
        } else {
            // 表示順を取得する
            Integer sortOrder = optionRepository.getMaxSortOrder(inputDto.getStoreId());
            if (Objects.isNull(sortOrder)) {
                sortOrder = 0;
            }
            // オプションコードの採番を行う
            String seqNo = optionRepository.getSeqNo(inputDto.getStoreId());

            // オプション情報を保存する
            optionRepository.insertOption(inputDto.getStoreId(),
                inputDto.getOptionTypeCd(), seqNo, inputDto.getOptionName(),
                sortOrder + 1, userOperCd, dateTime);
        }
    }

    /**
     * オプション削除.
     *
     * @param inputDto オプション情報
     */
    @Override
    @Transactional
    public void deleteOption(DeleteOptionInputDto inputDto) {

        List<String> optionCdList = new ArrayList<>();
        for (OptionCodeDto optionCode : inputDto.getOptionList()) {
            optionCdList.add(optionCode.getOptionCode());
        }

        List<Integer> deleteBeforeItemList = itemOptionRepository
            .getItemList(inputDto.getStoreId(), optionCdList);

        // オプション情報を削除する
        for (OptionCodeDto optionCode : inputDto.getOptionList()) {
            optionRepository
                .deleteByoptionCodeList(inputDto.getStoreId(), optionCode.getOptionCode(),
                    optionCode.getOptionTypeCd());
        }

        itemOptionRepository.deleteItemOptionCode(inputDto.getStoreId(), optionCdList);

        List<Integer> deleteAfterItemList = itemOptionRepository
            .gethasOptionItemList(inputDto.getStoreId());

        itemRepository
            .updateItemOptionFlag(inputDto.getStoreId(), deleteBeforeItemList, deleteAfterItemList,
                getUserOperCd(), DateUtil.getNowDateTime());
    }

    /**
     * オプション順番情報編集.
     *
     * @param inputDto オプションコード情報
     */
    @Override
    @Transactional
    public void changeOptionSortOrder(ChangeOptionSortOrderInputDto inputDto) {

        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        List<OptionCodeDto> optionSortOrderList = inputDto.getOptionSortOrderList();

        for (int i = 0; i < optionSortOrderList.size(); i++) {
            // オプション情報をロックする
            MOption option = optionRepository
                .findByStoreIdAndOptionTypeCdAndOptionCodeForLock(inputDto.getStoreId(),
                    optionSortOrderList.get(i).getOptionCode());

            // オプションがNULLである場合
            if (option == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // オプション表示順を更新する
            optionRepository.updateOptionSort(inputDto.getStoreId(), inputDto.getOptionTypeCd(),
                optionSortOrderList.get(i).getOptionCode(), i + 1,
                userOperCd, dateTime);
        }
    }

    /**
     * オプション順番一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return オプション順番情報
     */
    @Override
    public GetOptionSortOrderListOutputDto getOptionSortOrderList(
        GetOptionSortOrderListInputDto inputDto) {

        // オプション順番情報を取得する
        List<Map<String, Object>> optionSortOrderListTmp =
            optionRepository.getOptionSortOrderList(inputDto.getStoreId(),
                inputDto.getLanguages(), inputDto.getOptionTypeCd());

        // オプション順番情報を設定する
        List<GetOptionList> optionSortOrderList = new ArrayList<>();
        optionSortOrderListTmp.forEach(stringObjectMap -> optionSortOrderList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetOptionList.class)));

        GetOptionSortOrderListOutputDto output = new GetOptionSortOrderListOutputDto();

        output.setOptionSortOrderList(optionSortOrderList);

        return output;
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
