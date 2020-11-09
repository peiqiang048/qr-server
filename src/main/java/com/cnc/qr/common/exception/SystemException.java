package com.cnc.qr.common.exception;

public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * exception code.
     */
    private final String code;

    /**
     * message arguments.
     */
    private final Object[] args;

    /**
     * Constructor<br>.
     * <p>
     * message to be displayed and underlying cause of exception can be specified.
     * </p>
     *
     * @param code    ExceptionCode
     * @param message message to be displayed
     * @param cause   underlying cause of exception
     * @param args
     */
    public SystemException(String code, String message, Throwable cause, Object... args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    /**
     * Constructor<br>.
     * <p>
     * message to be displayed can be specified.
     * </p>
     *
     * @param code    ExceptionCode
     * @param message message to be displayed
     * @param args
     */
    public SystemException(String code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    /**
     * Constructor<br>.
     * <p>
     * underlying cause of exception can be specified.
     * </p>
     *
     * @param code  ExceptionCode
     * @param cause underlying cause of exception
     * @param args
     */
    public SystemException(String code, Throwable cause, Object... args) {
        super(cause);
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}
