package com.cnc.qr.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.dto.AcountDTO;
import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.GetReceivablesInfoInputDto;
import com.cnc.qr.core.order.model.GetReceivablesInfoOutputDto;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.security.jwt.JWTFilter;
import com.cnc.qr.security.jwt.TokenProvider;
import com.cnc.qr.security.model.AppJwtToken;
import com.cnc.qr.security.model.AppUserAuth;
import com.cnc.qr.security.model.ControlDto;
import com.cnc.qr.security.model.StoreJwtToken;
import com.cnc.qr.security.model.StoreUserAuth;
import com.cnc.qr.security.until.SecurityUtils;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 認証コントローラ.
 */
@RestController
public class AuthUserController {

    /**
     * ユーザ認証情報取得.
     */
    @Autowired
    @Qualifier("AuthenticationManagerImpl")
    AuthenticationManager authenticationManager;

    /**
     * トークン処理クラス.
     */
    @Autowired
    private TokenProvider tokenProvider;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 店舗受付報取得.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;

    /**
     * 認証トークン取得.
     *
     * @param appUserAuth リクエストパラメータ
     * @return 認証情報
     */
    @PostMapping(UrlConstants.CSMB_GET_TOKEN)
    public ResponseEntity<AppJwtToken> getCustomerToken(
        @Valid @RequestBody AppUserAuth appUserAuth) {

        // 結果設定
        AppJwtToken outputResource = new AppJwtToken();

        // トークン取得
        if (StringUtils.isNotEmpty(appUserAuth.getReceivablesId())) {
            String jwt = storeInfoService
                .getTokenByReceivablesId(appUserAuth.getStoreId(), appUserAuth.getReceivablesId());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders
                .add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + StringUtils.trimToEmpty(jwt));
            return new ResponseEntity<>(outputResource, httpHeaders, HttpStatus.OK);
        }

        // ユーザ認証情報設定
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(env.getProperty("qr.env.mobile.login.user"),
                env.getProperty("qr.env.mobile.login.password"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("storeId", appUserAuth.getStoreId()); // 店舗ID
        jsonObject.put("userCd", CommonConstants.OPER_CD_MOBILE); // 登録者スマホ
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
                storeInfoService.getTokenValidityInMilliseconds(appUserAuth.getStoreId()));
        }

        outputResource.setAccessToken(jwt);

        // 出前ではない場合
        if (StringUtils.isNotEmpty(appUserAuth.getAppId()) && StringUtils
            .isNotEmpty(appUserAuth.getAppKey())) {
            // 店舗受付情報取得
            GetReceivablesInfoInputDto inputDto = new GetReceivablesInfoInputDto();
            inputDto.setStoreId(appUserAuth.getStoreId());
            inputDto.setCustomerCount(Integer.parseInt(appUserAuth.getCustomerCount()));
            GetReceivablesInfoOutputDto outputDto = storeInfoService.getReceivablesInfo(inputDto);
            outputResource.setReceivablesId(outputDto.getReceivablesId());
            outputResource.setReceivablesNo(outputDto.getReceivablesNo());

            // トークン登録
            if (StringUtils.isNotEmpty(jwt)) {
                storeInfoService
                    .storeTokenRegister(appUserAuth.getStoreId(), outputDto.getReceivablesId(),
                        jwt);
            }
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(outputResource, httpHeaders, HttpStatus.OK);
    }

    /**
     * トークン認証.
     *
     * @return 認証結果
     */
    @GetMapping(UrlConstants.CSMB_TOKEN_VERIFICATION)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CommonOutputResource tokenVerification() {

        // アウトプット情報を作成する
        return new CommonOutputResource();
    }

    /**
     * 会計タブレット/店員スマホログイン.
     *
     * @param login リクエストパラメータ
     * @return 認証情報
     */
    @PostMapping({UrlConstants.STPD_LOGIN_URL, UrlConstants.STMB_LOGIN_URL})
    public ResponseEntity<StoreJwtToken> authorize(@Valid @RequestBody StoreUserAuth login) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("storeId", login.getStoreId()); // 店舗ID
        authenticationToken.setDetails(jsonObject.toString());
        Authentication authentication = this.authenticationManager
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // boolean rememberMe = (login.getRememberMe() == null) ? false : login.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, true,
            storeInfoService.getTokenValidityInMilliseconds(login.getStoreId()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // プリンター印刷（ポーリングタイム）
        Long loopInterval = storeInfoService.getPrintPollMilliseconds(login.getStoreId());
        // コントロール条件取得
        ControlDto controlDto = storeInfoService.getStoreControl(login.getStoreId());
        // レスポス
        StoreJwtToken storeJwtToken = new StoreJwtToken();
        storeJwtToken.setIdToken(jwt);
        storeJwtToken.setLoopInterval(loopInterval);
        // 予約機能表示標識
        storeJwtToken.setReservationDisplayFlag(controlDto.getReservationDisplayFlag());
        // 出前機能表示標識
        storeJwtToken.setDeliveryDisplayFlag(controlDto.getDeliveryDisplayFlag());
        // 前後支払標識
        storeJwtToken.setBeforeAfterPaymentFlag(controlDto.getBeforeAfterPaymentFlag());
        // 客用スマホ使用可能標識
        storeJwtToken.setSmartPhonesAvailableFlag(controlDto.getSmartPhonesAvailableFlag());
        // 音声注文使用可能標識
        storeJwtToken.setVoiceOrderAvailableFlag(controlDto.getVoiceOrderAvailableFlag());

        return new ResponseEntity<>(storeJwtToken, httpHeaders,
            HttpStatus.OK);
    }


    /**
     * PC ログイン.
     *
     * @param login リクエストパラメータ
     * @return 認証情報
     */
    @PostMapping(UrlConstants.PC_LOGIN_URL)
    public ResponseEntity<StoreJwtToken> authorizeMg(@Valid @RequestBody StoreUserAuth login) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("businessId", login.getBusinessId()); // ビジネスID
        jsonObject.put("userCd", CommonConstants.OPER_CD_STORE_PC); // 登録者スマホ
        authenticationToken.setDetails(jsonObject.toString());
        Authentication authentication = this.authenticationManager
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // boolean rememberMe = (login.getRememberMe() == null) ? false : login.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, true,
            storeInfoService.getTokenValidityInMilliseconds(login.getBusinessId()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        AcountDTO acountDto = storeInfoService
            .getUserWithAuthorities(login.getBusinessId(), login.getUserId());
        String storeId = acountDto.getStoreList().get(0).getStoreId();
        StoreJwtToken storeJwtToken = new StoreJwtToken();
        storeJwtToken.setIdToken(jwt);
        storeJwtToken.setStoreId(storeId);
        // コントロール条件取得
        ControlDto controlDto = storeInfoService.getStoreControl(storeId);
        // 予約機能表示標識
        storeJwtToken.setReservationDisplayFlag(controlDto.getReservationDisplayFlag());
        // 出前機能表示標識
        storeJwtToken.setDeliveryDisplayFlag(controlDto.getDeliveryDisplayFlag());
        // 前後支払標識
        storeJwtToken.setBeforeAfterPaymentFlag(controlDto.getBeforeAfterPaymentFlag());
        return new ResponseEntity<>(storeJwtToken, httpHeaders,
            HttpStatus.OK);
    }

    /**
     * ユーザアカウント情報.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be
     *                          returned.
     */
    @GetMapping(UrlConstants.PC_GET_ACCOUNT_URL)
    public AcountDTO getAccount(
        @RequestParam(value = "businessId", required = false) String businessId) {
        // ユーザID取得
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Stream<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority);
            AcountDTO accountData = storeInfoService
                .getUserWithAuthorities(businessId, userInfo.get());
            accountData.setAuthorities(authorities.collect(Collectors.toSet()));
            return accountData;
        } else {
            throw new AccountResourceException("User could not be found");
        }
    }


    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }
}
