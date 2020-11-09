package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MCategory;
import com.cnc.qr.common.entity.RParentCategory;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCategoryRepository;
import com.cnc.qr.common.repository.RCategoryItemRepository;
import com.cnc.qr.common.repository.RParentCategoryRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.CategoryItem;
import com.cnc.qr.core.pc.model.ChangeCategorySortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCategoryListInputDto;
import com.cnc.qr.core.pc.model.GetCategoryListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryOutputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
import com.cnc.qr.core.pc.model.RegistCategoryInputDto;
import com.cnc.qr.core.pc.service.CategoryService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * カテゴリー情報取得サービス実装クラス.
 */
@Service
public class CategoryServiceImpl implements CategoryService {


    /**
     * カテゴリーマスタリポジトリ.
     */
    @Autowired
    private MCategoryRepository categoryRepository;

    /**
     * カテゴリー関連リポジトリ.
     */
    @Autowired
    private RParentCategoryRepository parentCategoryRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * カテゴリ商品関連リポジトリ.
     */
    @Autowired
    private RCategoryItemRepository categoryItemRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリー情報
     */
    @Override
    public GetCategoryListOutputDto getCategory(GetCategoryListInputDto inputDto,
        Pageable pageable) {

        // カテゴリー情報取得
        Page<Map<String, Object>> categoryMap;

        if (null == inputDto.getParentCategoryId()) {

            categoryMap = categoryRepository
                .getParentCategoryData(inputDto.getStoreId(), inputDto.getLanguages(),
                    StringUtils.isEmpty(inputDto.getCategoryName()) ? StringUtils.EMPTY
                        : inputDto.getCategoryName(), pageable);
        } else {

            categoryMap = parentCategoryRepository
                .getCategoryData(inputDto.getStoreId(), inputDto.getParentCategoryId(),
                    inputDto.getLanguages(),
                    StringUtils.isEmpty(inputDto.getCategoryName()) ? StringUtils.EMPTY
                        : inputDto.getCategoryName(), pageable);
        }

        // カテゴリー情報を設定する
        List<CategoryItem> categoryList = new ArrayList<>();
        categoryMap.forEach(stringObjectMap -> categoryList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), CategoryItem.class)));

        GetCategoryListOutputDto output = new GetCategoryListOutputDto();

        output.setCategoryList(new PageImpl<>(categoryList,
            categoryMap.getPageable(), categoryMap.getTotalPages()));

        output.setTotalCount(categoryMap.getTotalElements());

        return output;
    }

    /**
     * カテゴリー明細情報取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリーカテゴリー情報
     */
    @Override
    public GetCategoryOutputDto getCategoryInfo(GetCategoryInputDto inputDto) {

        // カテゴリー情報取得
        GetCategoryOutputDto categoryInfo =
            categoryRepository.getCategoryInfo(inputDto.getStoreId(), inputDto.getCategoryId());

        // 検索結果0件の場合
        if (null == categoryInfo) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.064", (Object) null));
        }

        return categoryInfo;
    }

    /**
     * 親カテゴリーID情報取得.
     *
     * @param inputDto 取得条件
     * @return 親カテゴリーID情報
     */
    @Override
    public List<GetCategoryIdListOutputDto> getParentCategoryIdList(GetCategoryInputDto inputDto) {

        // 親カテゴリーID取得
        List<GetCategoryIdListOutputDto> parentCategoryIdList = parentCategoryRepository
            .getParentCategoryIdList(
                inputDto.getStoreId(), inputDto.getCategoryId());

        parentCategoryIdList.forEach(categoryIdData -> {
            categoryIdData.setCategoryName(JSONObject.parseObject(
                categoryIdData.getCategoryName()).getString(inputDto.getLanguages()));
        });

        return parentCategoryIdList;
    }

    /**
     * 親カテゴリーリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 親商品カテゴリー情報
     */
    @Override
    public List<GetCategoryList> getParentCategoryList(GetCategoryInputDto inputDto) {

        // カテゴリーリスト取得
        List<GetCategoryList> parentCategoryList = categoryRepository.getParentCategoryList(
            inputDto.getStoreId());
        parentCategoryList.forEach(categoryData -> {
            categoryData.setCategoryName(JSONObject.parseObject(
                categoryData.getCategoryName()).getString(inputDto.getLanguages()));
        });

        return parentCategoryList;
    }

    /**
     * カテゴリー編集.
     *
     * @param inputDto カテゴリー情報
     */
    @Override
    @Transactional
    public void registCategory(RegistCategoryInputDto inputDto) {

        try {
            // カテゴリーID
            Integer categoryId = inputDto.getCategoryId();

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            if (null != categoryId) {

                MCategory category = categoryRepository.findByStoreIdAndCategoryIdAndDelFlag(
                    inputDto.getStoreId(), inputDto.getCategoryId(), Flag.OFF.getCode());

                if (category.getGradation() == 1 && inputDto.getGradation() == 2) {

                    Integer countChild = parentCategoryRepository.getChild(inputDto.getStoreId(),
                        inputDto.getCategoryId());

                    if (countChild != 0) {

                        throw new BusinessException("2001",
                            ResultMessages.error().add("e.qr.ph.067", (Object) null));
                    }
                }

                if (category.getGradation() == 2 && inputDto.getGradation() == 1) {

                    Integer itemCount = categoryItemRepository.getItemCount(inputDto.getStoreId(),
                        inputDto.getCategoryId());

                    if (itemCount != 0) {

                        throw new BusinessException("2001",
                            ResultMessages.error().add("e.qr.ph.068", (Object) null));
                    }
                }
            }

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 商品IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("m_category"); // テーブル名
            getSeqNoInputDto.setItem("category_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            int sortOrder = 0;

            if (inputDto.getGradation() == 1) {

                // 一级分类最大顺序取得
                Integer maxSortOrder = categoryRepository.getMaxSortOrder(inputDto.getStoreId());

                // 分类顺序设定
                if (null == maxSortOrder) {

                    sortOrder = 1;
                } else {

                    sortOrder = maxSortOrder + 1;
                }
            }

            if (null == categoryId) {

                // 创建分类数据
                categoryRepository.insertCategory(inputDto.getStoreId(), getSeqNo.getSeqNo(),
                    inputDto.getCategoryName(), sortOrder, inputDto.getGradation(), userOperCd,
                    dateTime);

                // 创建分类关联数据
                if (inputDto.getGradation() == 2) {

                    List<RParentCategory> parentCategoryList = new ArrayList<RParentCategory>();
                    List<GetCategoryIdListOutputDto> parentCategoryData = inputDto
                        .getParentCategoryIdList();
                    for (GetCategoryIdListOutputDto parentCategoryDatum : parentCategoryData) {

                        RParentCategory parentCategory = new RParentCategory();
                        parentCategory.setStoreId(inputDto.getStoreId());
                        parentCategory
                            .setParentCategoryId(parentCategoryDatum.getCategoryId());
                        parentCategory.setChildCategoryId(getSeqNo.getSeqNo());
                        Integer sort = parentCategoryRepository.getMaxSort(inputDto.getStoreId(),
                            parentCategoryDatum.getCategoryId());
                        if (null == sort) {
                            parentCategory.setSortOrder(1);
                        } else {
                            parentCategory.setSortOrder(sort + 1);
                        }
                        parentCategory.setDelFlag(Flag.OFF.getCode());
                        parentCategory.setInsOperCd(userOperCd);
                        parentCategory.setInsDateTime(dateTime);
                        parentCategory.setUpdOperCd(userOperCd);
                        parentCategory.setUpdDateTime(dateTime);
                        parentCategory.setVersion(0);
                        parentCategoryList.add(parentCategory);
                    }
                    parentCategoryRepository.saveAll(parentCategoryList);
                }
            } else {

                MCategory category = categoryRepository.findByStoreIdAndCategoryIdAndDelFlag(
                    inputDto.getStoreId(), inputDto.getCategoryId(), Flag.OFF.getCode());

                if (category.getGradation().intValue() == inputDto.getGradation().intValue()) {

                    // 层次不变修改分类
                    categoryRepository.updateCategory(inputDto.getStoreId(), categoryId,
                        inputDto.getCategoryName(), category.getSortOrder(),
                        inputDto.getGradation(),
                        userOperCd, dateTime);
                } else if (inputDto.getGradation() == 1) {

                    // 层次2变1修改分类
                    categoryRepository.updateCategory(inputDto.getStoreId(), categoryId,
                        inputDto.getCategoryName(), sortOrder, inputDto.getGradation(),
                        userOperCd, dateTime);
                } else {

                    // 层次1变2修改分类
                    categoryRepository.updateCategory(inputDto.getStoreId(), categoryId,
                        inputDto.getCategoryName(), 0, inputDto.getGradation(),
                        userOperCd, dateTime);
                }

                if (category.getGradation() == 1
                    && inputDto.getGradation() == 2) {

                    List<RParentCategory> parentCategoryList = new ArrayList<RParentCategory>();
                    List<GetCategoryIdListOutputDto> parentCategoryData = inputDto
                        .getParentCategoryIdList();
                    for (GetCategoryIdListOutputDto parentCategoryDatum : parentCategoryData) {

                        RParentCategory parentCategory = new RParentCategory();
                        parentCategory.setStoreId(inputDto.getStoreId());
                        parentCategory
                            .setParentCategoryId(parentCategoryDatum.getCategoryId());
                        parentCategory.setChildCategoryId(categoryId);
                        Integer sort = parentCategoryRepository.getMaxSort(inputDto.getStoreId(),
                            parentCategoryDatum.getCategoryId());
                        if (null == sort) {
                            parentCategory.setSortOrder(1);
                        } else {
                            parentCategory.setSortOrder(sort + 1);
                        }
                        parentCategory.setDelFlag(Flag.OFF.getCode());
                        parentCategory.setInsOperCd(userOperCd);
                        parentCategory.setInsDateTime(dateTime);
                        parentCategory.setUpdOperCd(userOperCd);
                        parentCategory.setUpdDateTime(dateTime);
                        parentCategory.setVersion(0);
                        parentCategoryList.add(parentCategory);
                    }
                    parentCategoryRepository.saveAll(parentCategoryList);
                } else if (category.getGradation() == 2
                    && inputDto.getGradation() == 1) {

                    // カテゴリー関連情報削除
                    parentCategoryRepository.deleteCategory(inputDto.getStoreId(), categoryId);
                } else if (category.getGradation() == 2
                    && inputDto.getGradation() == 2) {

                    List<RParentCategory> categorySort =
                        parentCategoryRepository.findByStoreIdAndChildCategoryIdAndDelFlag(
                            inputDto.getStoreId(), categoryId, Flag.OFF.getCode());

                    List<GetCategoryIdListOutputDto> parentIdList = inputDto
                        .getParentCategoryIdList();

                    boolean addBool;

                    List<Integer> addParentIdList = new ArrayList<>();

                    for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : parentIdList) {

                        addBool = true;

                        for (RParentCategory parentCategory : categorySort) {
                            if (getCategoryIdListOutputDto.getCategoryId()
                                .equals(parentCategory.getParentCategoryId())) {
                                addBool = false;
                            }
                        }

                        if (addBool) {

                            addParentIdList.add(getCategoryIdListOutputDto.getCategoryId());
                        }
                    }

                    boolean delBool;

                    List<Integer> delParentIdList = new ArrayList<>();

                    for (RParentCategory parentCategory : categorySort) {

                        delBool = true;

                        for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : parentIdList) {

                            if (parentCategory.getParentCategoryId()
                                .equals(getCategoryIdListOutputDto.getCategoryId())) {

                                delBool = false;
                            }
                        }

                        if (delBool) {

                            delParentIdList.add(parentCategory.getParentCategoryId());
                        }
                    }

                    List<RParentCategory> parentCategoryList = new ArrayList<>();

                    for (Integer integer : addParentIdList) {

                        RParentCategory parentCategory = new RParentCategory();
                        parentCategory.setStoreId(inputDto.getStoreId());
                        parentCategory.setParentCategoryId(integer);
                        parentCategory.setChildCategoryId(categoryId);
                        Integer sort = parentCategoryRepository.getMaxSort(inputDto.getStoreId(),
                            integer);
                        if (null == sort) {
                            parentCategory.setSortOrder(1);
                        } else {
                            parentCategory.setSortOrder(sort + 1);
                        }
                        parentCategory.setDelFlag(Flag.OFF.getCode());
                        parentCategory.setInsOperCd(userOperCd);
                        parentCategory.setInsDateTime(dateTime);
                        parentCategory.setUpdOperCd(userOperCd);
                        parentCategory.setUpdDateTime(dateTime);
                        parentCategory.setVersion(0);
                        parentCategoryList.add(parentCategory);
                    }
                    parentCategoryRepository.saveAll(parentCategoryList);

                    if (delParentIdList.size() != 0) {

                        parentCategoryRepository.deleteCategory(inputDto.getStoreId(),
                            categoryId, delParentIdList);
                    }
                }
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.065", (Object) null), ex);
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
     * カテゴリー情報削除.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void deleteCategory(DeleteCategoryInputDto inputDto) {

        // カテゴリーID取得
        List<Integer> categoryIdList = new ArrayList<>();
        for (GetCategoryIdListOutputDto categoryId : inputDto.getCategoryList()) {
            categoryIdList.add(categoryId.getCategoryId());
        }

        // カテゴリー情報取得·
        List<MCategory> categoryList = categoryRepository.getMstCategoryList(
            inputDto.getStoreId(), categoryIdList);

        // 一级分类ID数组初始化
        List<Integer> parentCategoryIdList = new ArrayList<>();

        // 二级分类ID数组初始化
        List<Integer> childCategoryIdList = new ArrayList<>();

        for (MCategory category : categoryList) {

            if (category.getGradation() == 1) {

                parentCategoryIdList.add(category.getCategoryId());
            } else {

                childCategoryIdList.add(category.getCategoryId());
            }
        }

        Integer hasChildCount = parentCategoryRepository.getChildCount(
            inputDto.getStoreId(), parentCategoryIdList);

        if (hasChildCount != 0) {

            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.069", (Object) null));
        }

        Integer hasItemCount = categoryItemRepository.getItemCount(
            inputDto.getStoreId(), childCategoryIdList);

        if (hasItemCount != 0) {

            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.070", (Object) null));
        }

        // 分类数据删除
        categoryRepository.updateDelFlagByCategoryId(inputDto.getStoreId(), categoryIdList,
            getUserOperCd(), DateUtil.getNowDateTime());

        // 分类关联数据删除
        parentCategoryRepository.deleteByChildIdList(inputDto.getStoreId(), categoryIdList);
    }

    /**
     * カテゴリー順番編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeCategorySortOrder(ChangeCategorySortOrderInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 判断是一级分类还是二级分类
            if (null == inputDto.getParentCategoryId()) {

                List<GetItemCategoryOutputDto> cateogrySortOrderList = inputDto
                    .getCategorySortOrderList();

                for (int i = 0; i < cateogrySortOrderList.size(); i++) {

                    categoryRepository.updateCategorySort(inputDto.getStoreId(),
                        cateogrySortOrderList.get(i).getItemCategoryId(), i + 1, userOperCd,
                        dateTime);
                }
            } else {

                // カテゴリー順番関連情報削除
                parentCategoryRepository.deleteByParentId(inputDto.getStoreId(),
                    inputDto.getParentCategoryId());

                // カテゴリー関連情報作成
                List<RParentCategory> parentCategoryList = new ArrayList<RParentCategory>();
                List<GetItemCategoryOutputDto> cateogrySortOrderList = inputDto
                    .getCategorySortOrderList();
                for (int i = 0; i < cateogrySortOrderList.size(); i++) {

                    RParentCategory parentCategory = new RParentCategory();
                    parentCategory
                        .setStoreId(inputDto.getStoreId());                                // 店舗ID
                    parentCategory.setParentCategoryId(
                        inputDto.getParentCategoryId());              // 親カテゴリーID
                    parentCategory.setChildCategoryId(
                        cateogrySortOrderList.get(i).getItemCategoryId()); // カテゴリーID
                    parentCategory
                        .setSortOrder(i + 1);                                              // 順番
                    parentCategory
                        .setDelFlag(Flag.OFF.getCode());                                   // 削除フラグ
                    parentCategory
                        .setInsOperCd(userOperCd);                                         // 登録者
                    parentCategory
                        .setInsDateTime(dateTime);                                         // 登録日時
                    parentCategory
                        .setUpdOperCd(userOperCd);                                         // 更新者
                    parentCategory
                        .setUpdDateTime(dateTime);                                         // 更新日時
                    parentCategory
                        .setVersion(0);                                                    // バージョン
                    parentCategoryList.add(parentCategory);
                }
                parentCategoryRepository.saveAll(parentCategoryList);
            }
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.071", (Object) null), ex);
        }
    }
}
