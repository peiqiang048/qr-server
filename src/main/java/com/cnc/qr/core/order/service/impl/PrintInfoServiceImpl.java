package com.cnc.qr.core.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.cnc.qr.common.shared.model.CustomerOrderInfoDto;
import com.cnc.qr.common.shared.model.KitchenOutputDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.QrCodeOutputDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.core.order.model.PrintCustomerKitchenDto;
import com.cnc.qr.core.order.service.PrintInfoService;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * プリンター伝票サービス実装クラス.
 */
@Service
public class PrintInfoServiceImpl implements PrintInfoService {


    /**
     * プリンター伝票サービス.
     */
    @Autowired
    private PrintDataSharedService printDataSharedService;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * QRコード印刷.
     *
     * @param inputDto 取得条件
     * @return QRコード印刷データ取得
     */
    @Override
    public QrCodeOutputDto getPrintQrCode(QrCodeInputDto inputDto) {

        //QRコード印刷
        return JSON
            .parseObject(printDataSharedService.getQrCodePrintData(inputDto),
                QrCodeOutputDto.class);
    }


    /**
     * 会計印刷.
     *
     * @param inputDto 取得条件
     * @return 会計印刷
     */
    @Override
    public OrderAccountInfoDto getPrintOrderAccount(SlipInputDto inputDto) {

        // 会計印刷
        return printDataSharedService.getOrderAccountPrintData(inputDto);
    }


    /**
     * 客用と厨房印刷.
     *
     * @param inputDto 取得条件
     * @return 客用と厨房印刷
     */
    @Override
    public PrintCustomerKitchenDto getCustomerKitchen(SlipInputDto inputDto) {

        // 厨房
        KitchenOutputDto kitchenOutputDto = printDataSharedService
            .getKitchenPrintData(inputDto);
        // 客用
        CustomerOrderInfoDto customerOrderInfoDto = printDataSharedService
            .getCustomerOrderPrintData(inputDto);

        PrintCustomerKitchenDto printCustomerKitchenDto = new PrintCustomerKitchenDto();
        printCustomerKitchenDto.setKitchenOutputDto(kitchenOutputDto);
        printCustomerKitchenDto.setCustomerOrderInfoDto(customerOrderInfoDto);

        return printCustomerKitchenDto;
    }
}
