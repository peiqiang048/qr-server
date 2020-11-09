package com.cnc.qr.common.exception;

/**
 * ビジネス例外クラス.
 */
public class BusinessException extends ResultMessagesNotificationException {

    private static final long serialVersionUID = 1L;

    private final String resultCode;

    /**
     * Constructor for specify a message.
     * <p>
     * generate a {@link ResultMessages} instance of error type and add a message.
     * </p>
     *
     * @param message result message
     */
    public BusinessException(String resultCode, String message) {
        super(ResultMessages.error().add(ResultMessage.fromText(message)));
        this.resultCode = resultCode;
    }

    /**
     * Constructor for specify messages.
     * <p>
     * Takes multiple {@code String} messages as argument.
     * </p>
     *
     * @param messages {@link ResultMessages} instance
     */
    public BusinessException(String resultCode, ResultMessages messages) {
        super(messages);
        this.resultCode = resultCode;
    }

    /**
     * Constructor for specify messages and exception.
     * <p>
     * Takes multiple {@code String} messages and cause of exception as argument.
     * </p>
     *
     * @param messages {@link ResultMessages} instance
     * @param cause    {@link Throwable} instance
     */
    public BusinessException(String resultCode, ResultMessages messages, Throwable cause) {
        super(messages, cause);
        this.resultCode = resultCode;
    }

    /**
     * returns resultCode.
     *
     * @return resultCode
     */
    public String getResultCode() {
        return resultCode;
    }
}
