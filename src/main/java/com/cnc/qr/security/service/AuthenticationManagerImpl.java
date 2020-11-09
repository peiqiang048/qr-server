package com.cnc.qr.security.service;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.dto.AcountDTO;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.RUserRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.order.service.StoreInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 認証クラス.
 */
@Service("AuthenticationManagerImpl")
public class AuthenticationManagerImpl implements AuthenticationManager {

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    RUserRepository userRepository;

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    MStoreRepository storeRepository;

    /**
     * メッセージリソース.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 店舗受付報取得.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * ユーザ認証.
     *
     * @param authentication 認証情報
     * @return 権限情報
     */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        // ユーザ権限リスト
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // ユーザ権限取得
        if (authentication.getDetails() != null) {
            JSONObject jsonObject = JSONObject.parseObject(authentication.getDetails().toString());
            String storeId = "";
            String businessId = "";
            String userCd = jsonObject.getString("userCd");
            List<String> userGrantedList = new ArrayList<>();
            if (Objects.equals(userCd, CommonConstants.OPER_CD_STORE_PC)) {
                businessId = jsonObject.getString("businessId");
                AcountDTO acountDto = storeInfoService
                    .getUserWithAuthorities(businessId, authentication.getPrincipal().toString());

                storeId = acountDto.getStoreList().get(0).getStoreId();
                userGrantedList = userRepository.findBusinessUserRoleCode(businessId,
                    authentication.getPrincipal().toString(),
                    Md5Util.toMd5(authentication.getCredentials().toString()), storeId);

            } else {
                storeId = jsonObject.getString("storeId");
                if (Objects.equals(jsonObject.getString("storeChange"), "1")) {
                    userGrantedList = userRepository.findChangeStoreUserRoleCode(storeId,
                        authentication.getPrincipal().toString());
                } else {
                    userGrantedList = userRepository.findStoreUserRoleCode(storeId,
                        authentication.getPrincipal().toString(),
                        Md5Util.toMd5(authentication.getCredentials().toString()));
                }

            }
            userGrantedList = userGrantedList.stream().filter(s -> !Objects.isNull(s))
                .collect(Collectors.toList());
            // ユーザ情報存在しない場合
            if (CollectionUtils.isEmpty(userGrantedList)) {
                throw new UsernameNotFoundException(messageSource.getMessage("e.qr.ph.029",
                    new String[]{authentication.getPrincipal().toString()}, Locale.JAPAN));
            }

            // 登録情報更新
            if (!Objects.equals(userCd, CommonConstants.OPER_CD_MOBILE)) {
                if (!Objects.equals(userCd, CommonConstants.OPER_CD_STORE_PC)) {
                    MStore storeDto = storeRepository
                        .findByStoreIdAndDelFlag(storeId, Flag.OFF.getCode());
                    businessId = storeDto.getBusinessId();
                }

                // ユーザ情報取得
                List<RUser> userInfo = userRepository
                    .findByBusinessIdAndLoginIdAndDelFlag(businessId,
                        authentication.getPrincipal().toString(), Flag.OFF.getCode());
                RUser user = userInfo.get(0);
                // 上次登录时间
                user.setUserLastLoginTime(user.getUpdDateTime());
                // 登录次数
                user.setUserLoginCount(user.getUserLoginCount() + 1);
                // 更新日時
                user.setUpdDateTime(DateUtil.getNowDateTime());
                // 更新者
                user.setUpdOperCd(authentication.getPrincipal().toString());
                // バージョン
                user.setVersion(user.getVersion() + 1);
                // ユーザ情報更新
                userRepository.save(user);
            }

            grantedAuthorities = userGrantedList.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        }

        // 権限を設定する
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
            authentication.getCredentials(), grantedAuthorities);
    }
}
