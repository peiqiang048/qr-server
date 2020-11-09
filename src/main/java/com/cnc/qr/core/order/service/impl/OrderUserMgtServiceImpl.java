package com.cnc.qr.core.order.service.impl;

import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.RUserRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.order.model.RegistPasswordInputDto;
import com.cnc.qr.core.order.service.OrderUserMgtService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント管理サービス実装クラス.
 */
@Service
@Transactional
public class OrderUserMgtServiceImpl implements OrderUserMgtService {

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    private RUserRepository userRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * パスワード登録.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void registPassword(RegistPasswordInputDto inputDto) {

        MStore storeList = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // ユーザ情報取得
        List<RUser> userInfo = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(storeList.getBusinessId(),
                inputDto.getUserId(), Flag.OFF.getCode());

        // ユーザ情報存在しない場合
        if (userInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.060", (Object) null));
        }

        // 指定ユーザーIDのユーザ情報ロック
        RUser user = userRepository
            .findByUserIdForLock(storeList.getBusinessId(), userInfo.get(0).getUserId());

        // パスワード更新
        user.setUserPassword(Md5Util.toMd5(inputDto.getNewPassword()));
        user.setUpdOperCd(getUserOperCd());
        user.setUpdDateTime(DateUtil.getNowDateTime());
        user.setVersion(user.getVersion() + 1);
        userRepository.save(user);
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
