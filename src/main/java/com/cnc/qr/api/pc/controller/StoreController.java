package com.cnc.qr.api.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.pc.resource.ChangeStoreInputResource;
import com.cnc.qr.api.pc.resource.ChangeStoreOutputResource;
import com.cnc.qr.api.pc.resource.ChangeStoreUserAuth;
import com.cnc.qr.api.pc.resource.GetHomePageInfoInputResource;
import com.cnc.qr.api.pc.resource.GetHomePageInfoOutputResource;
import com.cnc.qr.api.pc.resource.GetStoreInfoInputResource;
import com.cnc.qr.api.pc.resource.GetStoreInfoOutputResource;
import com.cnc.qr.api.pc.resource.GetStoreInputResource;
import com.cnc.qr.api.pc.resource.GetStoreListInputResource;
import com.cnc.qr.api.pc.resource.GetStoreListOutputResource;
import com.cnc.qr.api.pc.resource.GetStoreOutputResource;
import com.cnc.qr.api.pc.resource.UploadFileOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.core.pc.model.ChangeStoreInputDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoInputDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoInputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInputDto;
import com.cnc.qr.core.pc.model.GetStoreListInputDto;
import com.cnc.qr.core.pc.model.GetStoreListOutputDto;
import com.cnc.qr.core.pc.model.GetStoreOutputDto;
import com.cnc.qr.core.pc.model.UploadFileInputDto;
import com.cnc.qr.core.pc.model.UploadFileOutputDto;
import com.cnc.qr.core.pc.service.StoreService;
import com.cnc.qr.security.jwt.JWTFilter;
import com.cnc.qr.security.jwt.TokenProvider;
import com.cnc.qr.security.model.ControlDto;
import com.cnc.qr.security.model.StoreJwtToken;
import com.github.dozermapper.core.Mapper;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 店舗コントローラ.
 */
@RestController
public class StoreController {

    /**
     * 店舗情報取得サービ.
     */
    @Autowired
    private StoreService storeService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;


    /**
     * 店舗受付報取得.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * ユーザ認証情報取得.
     */
    @Autowired
    @Qualifier("AuthenticationManagerImpl")
    AuthenticationManager authenticationManager;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;
    /**
     * トークン処理クラス.
     */
    @Autowired
    private TokenProvider tokenProvider;


    /**
     * 店舗一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_STORE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetStoreListOutputResource getStoreList(
        @Validated @RequestBody GetStoreListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetStoreListInputDto inputDto = beanMapper.map(inputResource, GetStoreListInputDto.class);
        inputDto.setStoreList(inputResource.getStoreList());
        // 店舗一覧情報取得サービス処理を実行する
        GetStoreListOutputDto outDto = storeService.getStoreList(inputDto);

        // インプット情報をDTOにセットする
        GetStoreListOutputResource outputDto = beanMapper
            .map(outDto, GetStoreListOutputResource.class);

        return outputDto;
    }


    /**
     * 店舗情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗情報
     */
    @PostMapping(UrlConstants.PC_GET_STORE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public GetStoreOutputResource getStore(
        @Validated @RequestBody GetStoreInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetStoreInputDto inputDto = beanMapper.map(inputResource, GetStoreInputDto.class);

        // 店舗情報取得サービス処理を実行する
        GetStoreOutputDto outDto = storeService.getStore(inputDto);

        // インプット情報をDTOにセットする
        GetStoreOutputResource outputDto = beanMapper.map(outDto, GetStoreOutputResource.class);

        return outputDto;
    }

    /**
     * 店舗情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_STORE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public ChangeStoreOutputResource changeStore(
        @Validated @RequestBody ChangeStoreInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeStoreInputDto inputDto = beanMapper.map(inputResource, ChangeStoreInputDto.class);
        inputDto.setDelStoreMediaList(inputResource.getDelStoreMediaList());
        inputDto.setStoreMediaList(inputResource.getStoreMediaList());

        // 店舗情報編集サービス処理を実行する
        storeService.changeStore(inputDto);

        return new ChangeStoreOutputResource();
    }

    /**
     * ホームページ情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return ホームページ情報
     */
    @PostMapping(UrlConstants.PC_GET_HOME_PAGE_INFO_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetHomePageInfoOutputResource getHomePageInfo(
        @Validated @RequestBody GetHomePageInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        GetHomePageInfoInputDto inputDto = beanMapper
            .map(inputResource, GetHomePageInfoInputDto.class);

        //  ホームページ情報取得処理を実行する
        GetHomePageInfoOutputDto outDto = storeService.getHomePageInfo(inputDto);

        //  インプット情報をDTOにセットする
        GetHomePageInfoOutputResource outputResource = new GetHomePageInfoOutputResource();
        outputResource.setStoreId(outDto.getStoreId());
        outputResource.setUserName(outDto.getUserName());
        outputResource.setStoreName(outDto.getStoreName());
        outputResource.setStoreLogoUrl(outDto.getStoreLogoUrl());
        outputResource.setLanguages(outDto.getLanguages());
        outputResource.setMenuList(outDto.getMenuList());
        outputResource.setPicList(outDto.getPicList());

        return outputResource;
    }

    /**
     * ファイルアップロード.
     *
     * @param storeId 店舗ID
     * @param useType バインド結果
     * @param file    利用区分
     * @return アップロード結果
     */
    @PostMapping(UrlConstants.PC_UPLOAD_FILE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public UploadFileOutputResource uploadFile(
        @RequestParam("storeId") String storeId, @RequestParam("useType") String useType,
        @RequestParam("file") MultipartFile file) {

        // 単項目チェック
        if (StringUtils.isEmpty(storeId) || StringUtils.isEmpty(useType) || file == null) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        UploadFileInputDto inputDto = new UploadFileInputDto();
        inputDto.setStoreId(storeId);
        inputDto.setUseType(useType);
        inputDto.setFile(file);

        //  店員サービス処理を実行する
        UploadFileOutputDto outputDto = storeService.uploadFile(inputDto);

        return beanMapper.map(outputDto, UploadFileOutputResource.class);
    }

    /**
     * 店舗情報取得情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗情報取得情報
     */
    @PostMapping(UrlConstants.PC_GET_STORE_INFO_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetStoreInfoOutputResource getStoreInfo(
        @Validated @RequestBody GetStoreInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        GetStoreInfoInputDto inputDto = beanMapper
            .map(inputResource, GetStoreInfoInputDto.class);

        //  店舗情報取得処理を実行する
        GetStoreInfoOutputDto outDto = storeService.getStoreInfo(inputDto);

        //  インプット情報をDTOにセットする
        GetStoreInfoOutputResource outputResource = new GetStoreInfoOutputResource();
        outputResource.setLanguages(outDto.getLanguages());
        outputResource.setMaxGradation(outDto.getMaxGradation());

        return outputResource;
    }

    /**
     * 会計タブレット/PC ログイン.
     *
     * @param login リクエストパラメータ
     * @return 認証情報
     */
    @PostMapping(UrlConstants.MT_CHANGE_STORE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public ResponseEntity<StoreJwtToken> authorize(@Valid @RequestBody ChangeStoreUserAuth login) {

        // ユーザ認証情報設定
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUserId(), null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("storeId", login.getStoreId()); // 店舗ID
        jsonObject.put("storeChange", "1");
        authenticationToken.setDetails(jsonObject.toString());

        // トークン生成
        Authentication authentication = this.authenticationManager
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "";
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        // ユーザ権限がある場合
        if (StringUtils.isNotEmpty(authorities)) {
            jwt = tokenProvider.createToken(authentication, false,
                storeInfoService.getTokenValidityInMilliseconds(login.getStoreId()));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        StoreJwtToken storeJwtToken = new StoreJwtToken();
        storeJwtToken.setIdToken(jwt);

        // コントロール条件取得
        ControlDto controlDto = storeInfoService.getStoreControl(login.getStoreId());
        // 予約機能表示標識
        storeJwtToken.setReservationDisplayFlag(controlDto.getReservationDisplayFlag());
        // 出前機能表示標識
        storeJwtToken.setDeliveryDisplayFlag(controlDto.getDeliveryDisplayFlag());
        // 前後支払標識
        storeJwtToken.setBeforeAfterPaymentFlag(controlDto.getBeforeAfterPaymentFlag());
        return new ResponseEntity<>(storeJwtToken, httpHeaders, HttpStatus.OK);
    }
}
