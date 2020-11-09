package com.cnc.qr.common.exception;

import com.cnc.qr.common.model.ApiError;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class ApiErrorCreator {

    @Autowired
    MessageSource messageSource;

    public ApiError createApiError(WebRequest request, String errorCode, String defaultErrorMessage,
        Object... arguments) {
        if (StringUtils.isNotEmpty(defaultErrorMessage)) {
            return new ApiError(errorCode, defaultErrorMessage);
        }
        String localizedMessage = messageSource
            .getMessage(errorCode, arguments, defaultErrorMessage, Locale.JAPAN);
        return new ApiError(errorCode, localizedMessage);
    }

    public ApiError createApiError(WebRequest request, String rootErrorCode, String errorCode,
        String defaultErrorMessage, Object... arguments) {
        if (StringUtils.isNotEmpty(defaultErrorMessage)) {
            return new ApiError(rootErrorCode, defaultErrorMessage);
        }
        String localizedMessage = messageSource
            .getMessage(errorCode, arguments, defaultErrorMessage, Locale.JAPAN);
        return new ApiError(rootErrorCode, localizedMessage);
    }

    public ApiError createResultMessagesApiError(WebRequest request, String rootErrorCode,
        ResultMessages resultMessages,
        String defaultErrorMessage) {
        ApiError apiError;
        if (resultMessages.getList().size() == 1) {
            ResultMessage resultMessage = resultMessages.iterator().next();
            apiError = createApiError(request, rootErrorCode, resultMessage.getCode(),
                resultMessage.getText(),
                resultMessage.getArgs());
        } else {
            apiError = createApiError(request, rootErrorCode, defaultErrorMessage);
            for (ResultMessage resultMessage : resultMessages.getList()) {
                apiError.addDetail(
                    createApiError(request, resultMessage.getCode(), resultMessage.getText(),
                        resultMessage.getArgs()));
            }
        }
        return apiError;
    }
}
