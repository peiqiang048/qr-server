package com.cnc.qr.common.exception;

import com.cnc.qr.common.constants.CodeConstants;
import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AppTokenException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public static final String PROBLEM_BASE_URL = "http://www.cncsys.co.jp";
    public static final URI RETURN_ERROR_CODE = URI.create(PROBLEM_BASE_URL);

    public AppTokenException(int statusCode) {
        super(RETURN_ERROR_CODE, CodeConstants.AppTokenStatus.getReasonPhrase(statusCode),
            Status.BAD_REQUEST);
    }
}
