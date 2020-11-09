package com.cnc.qr.security.component;

import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.security.until.SecurityUtils;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(CommonConstants.SYSTEM_ACCOUNT));
    }
}
