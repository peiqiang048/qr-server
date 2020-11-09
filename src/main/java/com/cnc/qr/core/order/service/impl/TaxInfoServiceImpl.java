package com.cnc.qr.core.order.service.impl;

import com.cnc.qr.common.constants.CodeConstants.CateringChargeType;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.TaxInfoDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.core.order.model.TaxInfoInputDto;
import com.cnc.qr.core.order.model.TaxInfoOutputDto;
import com.cnc.qr.core.order.model.TaxValueInputDto;
import com.cnc.qr.core.order.model.TaxValueOutputDto;
import com.cnc.qr.core.order.service.TaxInfoService;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 税情報サービス実装クラス.
 */
@Service
public class TaxInfoServiceImpl implements TaxInfoService {

    /**
     * 税情報マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

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
     * 税情報取得.
     *
     * @param inputDto 取得条件
     * @return 税情報
     */
    @Override
    public TaxInfoOutputDto getTaxInfo(TaxInfoInputDto inputDto) {

        // ビジネスIDを取得する
        MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 税情報を取得する
        List<Integer> itemList = new ArrayList<>();
        itemList.add(inputDto.getItemId());
        List<TaxInfoDto> taxInfoDto = itemRepository.getTaxInfo(store.getBusinessId(),
            inputDto.getStoreId(), ZonedDateTime.now(), itemList);

        // インプット情報をDTOにセットする
        return beanMapper.map(taxInfoDto.get(0), TaxInfoOutputDto.class);
    }

    /**
     * 税情報取得.
     *
     * @param inputDto 取得条件
     * @return 税情報
     */
    @Override
    public TaxValueOutputDto getTaxValue(TaxValueInputDto inputDto) {

        // 検索条件を設定する
        GetTaxValueInputDto taxValueInputDto = beanMapper.map(inputDto, GetTaxValueInputDto.class);
        taxValueInputDto.setItemList(inputDto.getItemList());

        // 税情報を取得する
        GetTaxValueOutputDto outputDto = itemInfoSharedService.getTaxValue(taxValueInputDto);

        // インプット情報をDTOにセットする
        return beanMapper.map(outputDto, TaxValueOutputDto.class);
    }

    /**
     * 配達費取得.
     *
     * @return 配達費
     */
    @Override
    public BigDecimal getCateringCharge(String storeId, BigDecimal taxTotal) {

        // 配達費初期化
        BigDecimal cateringCharge = BigDecimal.ZERO;

        MStore store = storeRepository.findByStoreIdAndDelFlag(storeId, Flag.OFF.getCode());

        if (null == store.getCateringChargeType()) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.032", (Object) null));
        }

        if (CateringChargeType.AMOUNT.getCode().equals(store.getCateringChargeType())) {

            cateringCharge = store.getCateringChargeAmount();
        } else {
            cateringCharge = taxTotal.multiply(
                store.getCateringChargePercent()).divide(new BigDecimal(100))
                .setScale(0, BigDecimal.ROUND_DOWN);
        }

        // 配達費
        return cateringCharge;
    }
}
