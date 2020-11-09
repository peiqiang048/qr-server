package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MTax;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MTaxRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.GetTaxInputDto;
import com.cnc.qr.core.pc.model.GetTaxOutputDto;
import com.cnc.qr.core.pc.model.RegistTaxInputDto;
import com.cnc.qr.core.pc.model.TaxDelInputDto;
import com.cnc.qr.core.pc.model.TaxDto;
import com.cnc.qr.core.pc.model.TaxInputDto;
import com.cnc.qr.core.pc.model.TaxListInputDto;
import com.cnc.qr.core.pc.model.TaxListOutputDto;
import com.cnc.qr.core.pc.model.TaxReliefApplyTypeDto;
import com.cnc.qr.core.pc.model.TaxRoundDto;
import com.cnc.qr.core.pc.model.TaxTypeDto;
import com.cnc.qr.core.pc.service.TaxService;
import com.cnc.qr.security.until.SecurityUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 税設定サービス実装クラス.
 */
@Service
@Transactional
public class TaxServiceImpl implements TaxService {

    /**
     * 税設定リポジトリ.
     */
    @Autowired
    private MTaxRepository taxRepository;

    /**
     * 税設定ー覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public TaxListOutputDto getTaxList(TaxListInputDto inputDto) {

        // 税設定一覧情報を取得する
        List<Map<String, Object>> taxInfoList = taxRepository
            .findTaxInfoList(inputDto.getBusinessId());

        // 税設定一覧情報を変換する
        List<TaxDto> taxList = new ArrayList<>();
        taxInfoList.forEach(stringObjectMap -> taxList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), TaxDto.class)));

        // 適用区分名を設定する
        for (TaxDto taxDto : taxList) {
            if (!"".equals(taxDto.getTaxReliefApplyTypeCode())) {
                for (CodeConstants.TaxReliefApplyType type : CodeConstants.TaxReliefApplyType
                    .values()) {
                    if (type.getCode().equals(taxDto.getTaxReliefApplyTypeCode())) {
                        taxDto.setTaxReliefApplyTypeName(type.getValue());
                    }
                }
            }
        }

        // アウトプット
        TaxListOutputDto outputDto = new TaxListOutputDto();
        outputDto.setTaxList(taxList);
        return outputDto;
    }

    /**
     * 税設定情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetTaxOutputDto getTax(GetTaxInputDto inputDto) {

        // アウトプット
        GetTaxOutputDto outputDto = new GetTaxOutputDto();

        // 更新の場合
        if (inputDto.getTaxId() != null) {
            // 税設定情報を取得する
            Map<String, Object> taxInfo = taxRepository
                .findTaxInfo(inputDto.getBusinessId(), inputDto.getTaxId());

            // 税設定情報を設定する
            outputDto = JSONObject
                .parseObject(JSONObject.toJSONString(taxInfo), GetTaxOutputDto.class);
        }

        // 税区分情報を取得する
        List<TaxTypeDto> taxClassificationDtoList = new ArrayList<>();
        TaxTypeDto taxTypeDto;

        for (CodeConstants.TaxType taxType : CodeConstants.TaxType.values()) {
            taxTypeDto = new TaxTypeDto();
            taxTypeDto.setTaxCode(taxType.getCode());
            taxTypeDto.setTaxName(taxType.getValue());
            taxClassificationDtoList.add(taxTypeDto);
        }

        // 税端数処理区分情報を取得する
        List<TaxRoundDto> taxRoundTypeDtoList = new ArrayList<>();
        TaxRoundDto taxRoundDto;

        for (CodeConstants.TaxRoundType taxRoundType : CodeConstants.TaxRoundType.values()) {
            taxRoundDto = new TaxRoundDto();
            taxRoundDto.setTaxRoundTypeCode(taxRoundType.getCode());
            taxRoundDto.setTaxRoundTypeName(taxRoundType.getValue());
            taxRoundTypeDtoList.add(taxRoundDto);
        }

        // 軽減税率適用区分を取得する
        List<TaxReliefApplyTypeDto> taxReliefApplyTypeDtoList = new ArrayList<>();
        TaxReliefApplyTypeDto taxReliefApplyTypeDto;

        for (CodeConstants.TaxReliefApplyType taxReliefApplyType : CodeConstants.TaxReliefApplyType
            .values()) {
            taxReliefApplyTypeDto = new TaxReliefApplyTypeDto();
            taxReliefApplyTypeDto.setTaxReliefApplyTypeCode(taxReliefApplyType.getCode());
            taxReliefApplyTypeDto.setTaxReliefApplyTypeName(taxReliefApplyType.getValue());
            taxReliefApplyTypeDtoList.add(taxReliefApplyTypeDto);
        }

        // 税区分情報を設定する
        outputDto.setTaxTypeList(taxClassificationDtoList);
        // 税端数処理区分を設定する
        outputDto.setTaxRoundTypeList(taxRoundTypeDtoList);
        // 軽減税率適用区分を設定する
        outputDto.setTaxReliefApplyTypeList(taxReliefApplyTypeDtoList);

        return outputDto;
    }

    /**
     * 税設定保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void saveTax(RegistTaxInputDto inputDto) {

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        Instant instant = Instant.parse(inputDto.getApplyDate());
        ZonedDateTime applyDate = ZonedDateTime
            .ofInstant(instant, ZoneId.of(CommonConstants.TIMEZONE_TOKYO));

        // 税ID空白ではない場合
        if (inputDto.getTaxId() != null) {

            // オプション情報をロックする
            MTax tax = taxRepository
                .findByBusinessIdAndTaxIdForLock(inputDto.getBusinessId(), inputDto.getTaxId());

            // オプションがNULLである場合
            if (tax == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 税設定情報を保存する
            tax.setTaxName(inputDto.getTaxName());
            tax.setTaxCode(inputDto.getTaxCode());
            tax.setTaxRateNormal(new BigDecimal(inputDto.getTaxRateNormal()));
            tax.setTaxRateRelief(new BigDecimal(inputDto.getTaxRateRelief()));
            tax.setTaxRoundType(inputDto.getTaxRoundType());
            tax.setTaxReliefApplyType(inputDto.getTaxReliefApplyType());
            tax.setApplyDate(applyDate);
            tax.setUpdOperCd(userOperCd);
            tax.setUpdDateTime(nowDateTime);
            taxRepository.save(tax);
        } else {

            // 表示順を取得する
            Integer sortOrder = taxRepository
                .getMaxSortOrder(inputDto.getBusinessId());

            // 税設定IDの採番を行う
            Integer seqNo = taxRepository
                .getSeqNo(inputDto.getBusinessId());

            MTax taxData = new MTax();
            // 税設定情報を保存する
            taxData.setBusinessId(inputDto.getBusinessId());
            taxData.setTaxId(Objects.isNull(seqNo) ? 1 : seqNo);
            taxData.setTaxName(inputDto.getTaxName());
            taxData.setTaxCode(inputDto.getTaxCode());
            taxData.setTaxRoundType(inputDto.getTaxRoundType());
            taxData.setTaxReliefApplyType(inputDto.getTaxReliefApplyType());
            taxData.setTaxRateNormal(new BigDecimal(inputDto.getTaxRateNormal()));
            taxData.setTaxRateRelief(new BigDecimal(inputDto.getTaxRateRelief()));
            taxData.setApplyDate(applyDate);
            taxData.setSortOrder(Objects.isNull(sortOrder) ? 1 : sortOrder + 1);
            taxData.setDelFlag(Flag.OFF.getCode());
            taxData.setVersion(0);
            taxData.setInsOperCd(userOperCd);
            taxData.setInsDateTime(nowDateTime);
            taxData.setUpdOperCd(userOperCd);
            taxData.setUpdDateTime(nowDateTime);
            taxRepository.save(taxData);
        }
    }

    /**
     * 税設定削除.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void delTax(TaxDelInputDto inputDto) {

        // 税ID取得
        List<Integer> taxIdList = new ArrayList<>();
        for (TaxInputDto taxDto : inputDto.getTaxIdList()) {
            taxIdList.add(taxDto.getTaxId());
        }
        // 登録更新者
        String userOperCd = getUserOperCd();
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 税設定削除
        taxRepository.updateDelFlagByTaxId(inputDto.getBusinessId(), taxIdList, userOperCd,
            nowDateTime);
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
