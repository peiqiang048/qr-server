package com.cnc.qr.config;

import com.cnc.qr.common.entity.OInspectionSettle;
import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jhipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jhipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder
                    .timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
        javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties
            .put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.cnc.qr.common.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.cnc.qr.common.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.cnc.qr.common.entity.User.class.getName());
            createCache(cm, com.cnc.qr.common.entity.Authority.class.getName());
            createCache(cm, com.cnc.qr.common.entity.User.class.getName() + ".authorities");
            createCache(cm, com.cnc.qr.common.entity.MBusiness.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MPaymentMethod.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MTax.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MCode.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MLicense.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MStore.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MTable.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MStoreMedium.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MReceipt.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MPrint.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RReceiptPrint.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MCategory.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RParentCategory.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MUnit.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MItem.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RCategoryItem.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MOption.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MSellOff.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MItemIndex.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MItemImage.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OOrderSummary.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OOrder.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OOrderDetails.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OCall.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OReceivables.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OPayment.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RUser.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RUserRole.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RRole.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RModel.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MBusiness.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MKitchen.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RKitchenPrint.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OOrderDetailsOption.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RRoleModel.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MOptionType.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RItemOption.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RItem.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MControl.class.getName());
            createCache(cm, com.cnc.qr.common.entity.PPaymentCompany.class.getName());
            createCache(cm, com.cnc.qr.common.entity.PPayment.class.getName());
            createCache(cm, com.cnc.qr.common.entity.PPaymentDetail.class.getName());
            createCache(cm, com.cnc.qr.common.entity.PTrioResultDetail.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MMenu.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RParentMenu.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RRoleMenu.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MTableIndex.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MSequence.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OItemReturn.class.getName());
            createCache(cm, com.cnc.qr.common.entity.PSbResultDetail.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RBuffetItem.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RTable.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MDevice.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MToken.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RMenuModel.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MArea.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MDeliveryArea.class.getName());
            createCache(cm, com.cnc.qr.common.entity.ODeliveryOrderSummary.class.getName());
            createCache(cm, com.cnc.qr.common.entity.MSpeechPhrases.class.getName());
            createCache(cm, com.cnc.qr.common.entity.RReservate.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OReservate.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OPrintQueue.class.getName());
            createCache(cm, OInspectionSettle.class.getName());
            createCache(cm, com.cnc.qr.common.entity.OTaxAmount.class.getName());

            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
