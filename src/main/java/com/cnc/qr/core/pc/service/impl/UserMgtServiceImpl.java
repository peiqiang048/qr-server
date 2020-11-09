package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.RoleId;
import com.cnc.qr.common.constants.CodeConstants.UserType;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.RModel;
import com.cnc.qr.common.entity.RRole;
import com.cnc.qr.common.entity.RRoleModel;
import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.common.entity.RUserRole;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.RModelRepository;
import com.cnc.qr.common.repository.RRoleModelRepository;
import com.cnc.qr.common.repository.RRoleRepository;
import com.cnc.qr.common.repository.RUserRepository;
import com.cnc.qr.common.repository.RUserRoleRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.pc.model.ChangeRoleStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeUserPasswordInputDto;
import com.cnc.qr.core.pc.model.ClassificationInfoDto;
import com.cnc.qr.core.pc.model.DeleteRoleInputDto;
import com.cnc.qr.core.pc.model.DeleteUserInputDto;
import com.cnc.qr.core.pc.model.GetRoleInputDto;
import com.cnc.qr.core.pc.model.GetRoleListInputDto;
import com.cnc.qr.core.pc.model.GetRoleListOutputDto;
import com.cnc.qr.core.pc.model.GetRoleOutputDto;
import com.cnc.qr.core.pc.model.GetUserInputDto;
import com.cnc.qr.core.pc.model.GetUserListInputDto;
import com.cnc.qr.core.pc.model.GetUserListOutputDto;
import com.cnc.qr.core.pc.model.GetUserOutputDto;
import com.cnc.qr.core.pc.model.ModelIdDto;
import com.cnc.qr.core.pc.model.ModelInfoDto;
import com.cnc.qr.core.pc.model.RegisterRoleInfoDto;
import com.cnc.qr.core.pc.model.RegisterRoleInputDto;
import com.cnc.qr.core.pc.model.RegisterUserInputDto;
import com.cnc.qr.core.pc.model.ResetUserPasswordInputDto;
import com.cnc.qr.core.pc.model.RoleIdDto;
import com.cnc.qr.core.pc.model.RoleInfoDto;
import com.cnc.qr.core.pc.model.UserIdDto;
import com.cnc.qr.core.pc.model.UserInfoDto;
import com.cnc.qr.core.pc.model.UserRoleInfoDto;
import com.cnc.qr.core.pc.service.UserMgtService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント管理サービス実装クラス.
 */
@Service
@Transactional
public class UserMgtServiceImpl implements UserMgtService {

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    private RUserRepository userRepository;


    /**
     * ユーザ関連役割テーブルリポジトリ.
     */
    @Autowired
    private RUserRoleRepository userRoleRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * ユーザ役割テーブルリポジトリ.
     */
    @Autowired
    private RRoleRepository roleRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 役割関連機能テーブルリポジトリ.
     */
    @Autowired
    private RRoleModelRepository roleModelRepository;

    /**
     * 機能テーブルリポジトリ.
     */
    @Autowired
    private RModelRepository modelRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * ユーザー一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return ユーザー一覧情報
     */
    @Override
    public GetUserListOutputDto getUserList(GetUserListInputDto inputDto, Pageable pageable) {

        // 商品情報取得
        Page<Map<String, Object>> userInfoMap;
        String currentUserLogin = null;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            currentUserLogin = userInfo.get();
        }

        // 事業者ログイン判断
        List<RUser> userList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(inputDto.getBusinessId(), currentUserLogin,
                Flag.OFF.getCode());
        List<RUserRole> userRoleList = userRoleRepository
            .findRUserRoleByStoreIdAndUserIdAndDelFlag(inputDto.getStoreId(),
                userList.get(0).getUserId(), Flag.OFF.getCode());
        String storeId = inputDto.getStoreId();
        if (userRoleList.stream().anyMatch(s -> s.getRoleId().equals(RoleId.BUSINESS.getCode()))) {

            // 事業者ログインの場合、すべてユーザ検索
            userInfoMap = userRepository
                .findBusinessUserByStoreId(inputDto.getBusinessId(), pageable,
                    CommonConstants.CODE_GROUP_USER_CLASSIFICATION,
                    CommonConstants.CODE_GROUP_STOP_FLAG);
        } else {

            // 事業者以外ログイン
            userInfoMap = userRepository
                .findUserByStoreId(inputDto.getBusinessId(), storeId, pageable,
                    CommonConstants.CODE_GROUP_USER_CLASSIFICATION,
                    CommonConstants.CODE_GROUP_STOP_FLAG, UserType.BACK.getCode());
        }

        //  商品情報を設定する
        List<UserInfoDto> userInfoList = new ArrayList<>();
        userInfoMap.forEach(stringObjectMap -> userInfoList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), UserInfoDto.class)));

        GetUserListOutputDto output = new GetUserListOutputDto();

        output.setUserList(new PageImpl<>(userInfoList,
            userInfoMap.getPageable(), userInfoMap.getTotalPages()));

        output.setTotalCount(userInfoMap.getTotalElements());

        return output;

    }

    /**
     * ユーザ情報取得.
     *
     * @param inputDto 取得条件
     * @return ユーザ情報
     */
    @Override
    public GetUserOutputDto getUser(GetUserInputDto inputDto) {

        // 結果DTO初期化
        GetUserOutputDto outDto = new GetUserOutputDto();

        // ユーザーID指定の場合
        if (inputDto.getUserId() != null) {
            // ユーザ情報取得
            RUser userInfo = userRepository
                .findByBusinessIdAndUserIdAndDelFlag(inputDto.getBusinessId(), inputDto.getUserId(),
                    Flag.OFF.getCode());

            // 検索結果0件の場合
            if (userInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.054", (Object) null));
            }
            List<Map<String, Object>> userClassificationMapList = userRepository
                .findStoreByUserId(inputDto.getBusinessId(), inputDto.getUserId());
            if (userClassificationMapList.size() > 0) {
                List<Map<String, Object>> userClassificationMapTempList = userClassificationMapList
                    .stream()
                    .filter(s -> Objects.equals(s.get("store_id"), inputDto.getStoreId()))
                    .collect(Collectors.toList());
                if (userClassificationMapTempList.size() > 0) {
                    // ユーザ区分
                    outDto.setClassification(
                        userClassificationMapTempList.get(0).get("user_classification").toString());
                }
            }

            // 選択した役割取得
            List<RoleInfoDto> userRoleInfo = userRoleRepository
                .findUserRoleByUserId(inputDto.getStoreId(), inputDto.getUserId());
            // ログインID
            outDto.setLoginId(userInfo.getLoginId());
            // ユーザ名
            outDto.setUserName(userInfo.getUserName());
            // 選択した役割
            outDto.setCheckedRoleList(userRoleInfo);


        }

        // 店舗役割取得
        List<RRole> storeRoleList = roleRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        String currentUserLogin = null;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            currentUserLogin = userInfo.get();
        }
        // 事業者ログイン判断
        List<RUser> userList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(inputDto.getBusinessId(), currentUserLogin,
                Flag.OFF.getCode());
        List<RUserRole> userRoleList = userRoleRepository
            .findRUserRoleByStoreIdAndUserIdAndDelFlag(inputDto.getStoreId(),
                userList.get(0).getUserId(), Flag.OFF.getCode());
        // 事業者ログインじゃない場合、
        if (!userRoleList.stream().filter(s -> s.getRoleId() == RoleId.BUSINESS.getCode()).findAny()
            .isPresent()) {
            //事業者役割を除く
            storeRoleList = storeRoleList.stream()
                .filter(s -> s.getRoleId() != RoleId.BUSINESS.getCode())
                .collect(Collectors.toList());

        }

        // 区分リスト
        List<ClassificationInfoDto> classificationList = new ArrayList<>();
        for (UserType userType : UserType.values()) {
            ClassificationInfoDto classificationInfoDto = new ClassificationInfoDto();
            classificationInfoDto.setClassificationKey(userType.getCode());
            classificationInfoDto.setClassificationText(userType.getValue());
            classificationList.add(classificationInfoDto);
        }
        outDto.setClassificationList(classificationList);
        // 役割リスト
        List<RoleInfoDto> roleList = new ArrayList<>();
        storeRoleList.forEach(role -> {
            RoleInfoDto roleInfoDto = new RoleInfoDto();
            roleInfoDto.setRoleId(role.getRoleId());
            roleInfoDto.setRoleName(role.getRoleName());
            roleList.add(roleInfoDto);
        });
        outDto.setRoleList(roleList);

        return outDto;
    }

    /**
     * ユーザ情報編集.
     *
     * @param inputDto ユーザ情報
     */
    @Override
    public void registerUser(RegisterUserInputDto inputDto) {

        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 編集の場合
        if (inputDto.getUserId() != null) {

            // ユーザ情報取得
            RUser userData = userRepository
                .findByUserIdForLock(inputDto.getBusinessId(), inputDto.getUserId());

            // 検索結果0件の場合
            if (userData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.054", (Object) null));
            }

            // ログインID存在チェック
            if (!Objects.equals(inputDto.getLoginId(), userData.getLoginId())) {
                checkLoginId(inputDto.getBusinessId(),
                    inputDto.getLoginId());
            }

            // ユーザ情報編集
            userData.setUserName(inputDto.getUserName());
            userData.setLoginId(inputDto.getLoginId());

            userData.setUpdOperCd(userOperCd);
            userData.setUpdDateTime(nowTime);
            userData.setVersion(userData.getVersion() + 1);

            // ユーザ情報更新
            userRepository.save(userData);

            // 登録の場合
        } else {
            // ログインID存在チェック
            checkLoginId(inputDto.getBusinessId(), inputDto.getLoginId());

            // ユーザーIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getBusinessId()); // 店舗ID
            getSeqNoInputDto.setTableName("r_user"); // テーブル名
            getSeqNoInputDto.setItem("user_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // ユーザ情報編集
            RUser user = new RUser();
            user.setBusinessId(inputDto.getBusinessId());
            user.setUserId(getSeqNo.getSeqNo());
            user.setLoginId(inputDto.getLoginId());
            user.setUserPassword(Md5Util.toMd5(CommonConstants.DEFAULT_PC_PASSWORD));
            user.setUserName(inputDto.getUserName());
            user.setUserLastLoginTime(nowTime);
            user.setUserLoginCount(0);

            user.setStopFlag(Flag.OFF.getCode());
            user.setDelFlag(Flag.OFF.getCode());
            user.setInsDateTime(nowTime);
            user.setInsOperCd(userOperCd);
            user.setUpdDateTime(nowTime);
            user.setUpdOperCd(userOperCd);
            user.setVersion(0);
            RUser saveResult = userRepository.save(user);

            // ユーザID設定
            inputDto.setUserId(saveResult.getUserId());
        }

        // ユーザ役割更新
        registerUserRole(inputDto, nowTime, userOperCd);
    }

    /**
     * ユーザ情報削除.
     *
     * @param inputDto ユーザ情報
     */
    @Override
    public void deleteUser(DeleteUserInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // ユーザーID取得
        List<Integer> userIdList = new ArrayList<>();
        for (UserIdDto userId : inputDto.getUserList()) {
            userIdList.add(userId.getUserId());
        }

        // 指定ユーザーID削除
        userRepository.updateDelFlagByUserId(inputDto.getBusinessId(), userIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定ユーザーIDの役割情報削除
        userRoleRepository.updateDelFlagByUserId(inputDto.getStoreId(), userIdList, userOperCd,
            DateUtil.getNowDateTime());
    }

    /**
     * ユーザパスワードリセット.
     *
     * @param inputDto ユーザ情報
     */
    @Override
    public void resetUserPassword(ResetUserPasswordInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // ユーザーID取得
        List<Integer> userIdList = new ArrayList<>();
        for (UserIdDto userId : inputDto.getUserList()) {
            userIdList.add(userId.getUserId());
        }
        MStore storeInfo = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // 指定ユーザパスワードリセット
        userRepository.resetUserPasswordByUserId(storeInfo.getBusinessId(), userIdList,
            Md5Util.toMd5(CommonConstants.DEFAULT_PC_PASSWORD), userOperCd,
            DateUtil.getNowDateTime());

    }

    /**
     * 役割一覧情報.
     *
     * @param inputDto 取得条件
     * @return 役割情報
     */
    @Override
    public GetRoleListOutputDto getRoleList(GetRoleListInputDto inputDto, Pageable pageable) {

        // 役割一覧情報取得
        Page<Map<String, Object>> roleInfoMap;
        List<Integer> roleIdList = new ArrayList<>();
        roleIdList.add(RoleId.BUSINESS.getCode());
        roleIdList.add(RoleId.SHOP_MANAGER.getCode());
        roleInfoMap = roleRepository
            .findRoleByStoreId(inputDto.getStoreId(), pageable,
                CommonConstants.CODE_GROUP_STOP_FLAG, roleIdList);

        //  役割情報を設定する
        List<UserRoleInfoDto> roleInfoList = new ArrayList<>();
        roleInfoMap.forEach(stringObjectMap -> roleInfoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), UserRoleInfoDto.class)));

        GetRoleListOutputDto output = new GetRoleListOutputDto();

        output.setRoleList(new PageImpl<>(roleInfoList,
            roleInfoMap.getPageable(), roleInfoMap.getTotalPages()));

        output.setTotalCount(roleInfoMap.getTotalElements());

        return output;
    }

    /**
     * 機能情報取得.
     *
     * @param inputDto 取得条件
     * @return 機能情報
     */
    @Override
    public GetRoleOutputDto getRole(GetRoleInputDto inputDto) {

        // 結果DTO初期化
        GetRoleOutputDto outDto = new GetRoleOutputDto();

        // 役割ID指定の場合
        if (inputDto.getRoleId() != null) {
            // 役割情報取得
            RRole roleInfo = roleRepository
                .findByStoreIdAndRoleIdAndDelFlag(inputDto.getStoreId(), inputDto.getRoleId(),
                    Flag.OFF.getCode());

            // 検索結果0件の場合
            if (roleInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.058", (Object) null));
            }

            // 選択した機能取得
            List<ModelInfoDto> roleModelInfo = roleModelRepository
                .findModelByRoleId(inputDto.getStoreId(), inputDto.getRoleId());
            // 役割名
            outDto.setRoleName(roleInfo.getRoleName());
            for (ModelInfoDto model : roleModelInfo) {
                model.setModelName(
                    JSONObject.parseObject(model.getModelName())
                        .getString(inputDto.getLanguages()));
            }
            // 選択した機能
            outDto.setCheckedModelList(roleModelInfo);
        }

        // 店舗機能取得
        List<RModel> modelList = modelRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());
        List<ModelInfoDto> storeModelInfo = new ArrayList<>();
        modelList.forEach(model -> {
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setModelId(model.getModelId().toString());
            modelInfoDto.setModelName(
                JSONObject.parseObject(model.getModelName()).getString(inputDto.getLanguages()));
            storeModelInfo.add(modelInfoDto);
        });
        // 機能リスト
        outDto.setModelList(storeModelInfo);

        return outDto;
    }

    /**
     * 役割情報編集.
     *
     * @param inputDto 役割情報
     */
    @Override
    public void registerRole(RegisterRoleInputDto inputDto) {

        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 編集の場合
        if (inputDto.getRoleId() != null) {

            // 役割情報取得
            RRole roleData = roleRepository
                .findByRoleIdForLock(inputDto.getStoreId(), inputDto.getRoleId());

            // 検索結果0件の場合
            if (roleData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.058", (Object) null));
            }

            // 役割情報編集
            roleData.setRoleName(inputDto.getRoleName());
            roleData.setUpdOperCd(userOperCd);
            roleData.setUpdDateTime(nowTime);
            roleData.setVersion(roleData.getVersion() + 1);

            // 役割情報更新
            roleRepository.save(roleData);

            // 登録の場合
        } else {

            // 役割ーIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("r_role"); // テーブル名
            getSeqNoInputDto.setItem("role_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 役割情報編集
            RRole role = new RRole();
            role.setStoreId(inputDto.getStoreId());
            role.setRoleId(getSeqNo.getSeqNo());
            role.setRoleName(inputDto.getRoleName());
            role.setStopFlag(Flag.OFF.getCode());
            role.setDelFlag(Flag.OFF.getCode());
            role.setInsDateTime(nowTime);
            role.setInsOperCd(userOperCd);
            role.setUpdDateTime(nowTime);
            role.setUpdOperCd(userOperCd);
            role.setVersion(0);
            RRole saveResult = roleRepository.save(role);

            // 役割ID設定
            inputDto.setRoleId(saveResult.getRoleId());
        }

        // 役割機能更新
        registerRoleModel(inputDto, nowTime, userOperCd);
    }

    /**
     * 役割情報削除.
     *
     * @param inputDto 役割情報
     */
    @Override
    public void deleteRole(DeleteRoleInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 役割ID取得
        List<Integer> roleIdList = new ArrayList<>();
        for (RoleIdDto roleId : inputDto.getRoleList()) {
            roleIdList.add(roleId.getRoleId());
        }

        // 指定役割ID削除
        roleRepository.updateDelFlagByRoleId(inputDto.getStoreId(), roleIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定役割IDの関連機能削除
        roleModelRepository.updateDelFlagByRoleId(inputDto.getStoreId(), roleIdList, userOperCd,
            DateUtil.getNowDateTime());
    }

    /**
     * 役割ステータス更新.
     *
     * @param inputDto 役割情報
     */
    @Override
    public void changeRoleStatus(ChangeRoleStatusInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 用户ID取得
        List<Integer> roleIdList = new ArrayList<>();
        for (RoleIdDto roleId : inputDto.getRoleList()) {
            roleIdList.add(roleId.getRoleId());
        }

        // 指定役割ステータス更新
        roleRepository
            .updateStopFlagByRoleId(inputDto.getStoreId(), roleIdList, inputDto.getClassification(),
                userOperCd, DateUtil.getNowDateTime());
    }

    /**
     * パスワード更新.
     *
     * @param inputDto 役割情報
     */
    @Override
    public void changeUserPassword(ChangeUserPasswordInputDto inputDto) {
        MStore storeInfo = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());
        // ユーザ情報取得
        RUser userInfo = userRepository
            .findByBusinessIdAndUserIdAndUserPasswordAndDelFlag(storeInfo.getBusinessId(),
                inputDto.getUserId(), Md5Util.toMd5(inputDto.getOldPassword()), Flag.OFF.getCode());

        // ユーザ情報存在しない場合
        if (userInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.060", (Object) null));
        }

        // 指定ユーザーIDのユーザ情報ロック
        RUser user = userRepository
            .findByUserIdForLock(storeInfo.getBusinessId(), inputDto.getUserId());

        // パスワード更新
        user.setUserPassword(Md5Util.toMd5(inputDto.getNewPassword()));
        user.setUpdOperCd(getUserOperCd());
        user.setUpdDateTime(DateUtil.getNowDateTime());
        user.setVersion(user.getVersion() + 1);
        userRepository.save(user);
    }

    /**
     * ログインID存在チェック.
     *
     * @param businessId ビジネスID
     * @param loginId    ログインID
     */
    private void checkLoginId(String businessId, String loginId) {
        List<RUser> userInfoList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(businessId, loginId, Flag.OFF.getCode());
        if (userInfoList.size() != 0) {
            throw new BusinessException("6001",
                ResultMessages.error().add("e.qr.ph.056", (Object) null));


        }
    }

    /**
     * ユーザ役割更新.
     *
     * @param inputDto   ユーザ情報
     * @param nowTime    現在日付
     * @param userOperCd 登録更新者
     */
    private void registerUserRole(RegisterUserInputDto inputDto, ZonedDateTime nowTime,
        String userOperCd) {
        List<MStore> storeList = new ArrayList<>();
        // 事業者
        if (inputDto.getCheckedRoleList().get(0).getRoleId() == RoleId.BUSINESS.getCode()) {
            storeList = storeRepository
                .findByBusinessIdAndDelFlag(inputDto.getBusinessId(), Flag.OFF.getCode());

            for (MStore store : storeList) {
                // 指定ユーザーIDの役割情報削除
                userRoleRepository.deleteUserRoleByUserId(store.getStoreId(), inputDto.getUserId());

                // ユーザー役割情報作成
                List<RUserRole> userRoleList = new ArrayList<>();
                for (RegisterRoleInfoDto roleInfo : inputDto.getCheckedRoleList()) {
                    RUserRole userRole = new RUserRole();
                    userRole.setStoreId(store.getStoreId());
                    userRole.setRoleId(roleInfo.getRoleId());
                    userRole.setUserId(inputDto.getUserId());
                    userRole.setUserClassification(inputDto.getClassification());
                    userRole.setDelFlag(Flag.OFF.getCode());
                    userRole.setInsDateTime(nowTime);
                    userRole.setInsOperCd(userOperCd);
                    userRole.setUpdDateTime(nowTime);
                    userRole.setUpdOperCd(userOperCd);
                    userRole.setVersion(0);
                    userRoleList.add(userRole);
                }
                userRoleRepository.saveAll(userRoleList);
            }
            // 店長 || 店員
        } else {
            // 指定ユーザーIDの役割情報削除
            userRoleRepository.deleteUserRoleByUserId(inputDto.getStoreId(), inputDto.getUserId());

            // ユーザー役割情報作成
            List<RUserRole> userRoleList = new ArrayList<>();
            for (RegisterRoleInfoDto roleInfo : inputDto.getCheckedRoleList()) {
                RUserRole userRole = new RUserRole();
                userRole.setStoreId(inputDto.getStoreId());
                userRole.setRoleId(roleInfo.getRoleId());
                userRole.setUserId(inputDto.getUserId());
                userRole.setUserClassification(inputDto.getClassification());
                userRole.setDelFlag(Flag.OFF.getCode());
                userRole.setInsDateTime(nowTime);
                userRole.setInsOperCd(userOperCd);
                userRole.setUpdDateTime(nowTime);
                userRole.setUpdOperCd(userOperCd);
                userRole.setVersion(0);
                userRoleList.add(userRole);
            }
            userRoleRepository.saveAll(userRoleList);
        }
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

    /**
     * 役割機能更新.
     *
     * @param inputDto   役割情報
     * @param nowTime    現在日付
     * @param userOperCd 登録更新者
     */
    private void registerRoleModel(RegisterRoleInputDto inputDto, ZonedDateTime nowTime,
        String userOperCd) {

        // 指定役割IDの関連機能情報削除
        roleModelRepository.deleteRoleModelByRoleId(inputDto.getStoreId(), inputDto.getRoleId());

        // 役割関連機能情報作成
        List<RRoleModel> roleModelList = new ArrayList<>();
        for (ModelIdDto modelIdInfo : inputDto.getCheckedModelList()) {
            RRoleModel roleModel = new RRoleModel();
            roleModel.setStoreId(inputDto.getStoreId());
            roleModel.setRoleId(inputDto.getRoleId());
            roleModel.setModelId(modelIdInfo.getModelId());
            roleModel.setDelFlag(Flag.OFF.getCode());
            roleModel.setInsDateTime(nowTime);
            roleModel.setInsOperCd(userOperCd);
            roleModel.setUpdDateTime(nowTime);
            roleModel.setUpdOperCd(userOperCd);
            roleModel.setVersion(0);
            roleModelList.add(roleModel);
        }
        roleModelRepository.saveAll(roleModelList);
    }
}
