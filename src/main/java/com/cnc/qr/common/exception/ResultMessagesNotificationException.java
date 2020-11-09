package com.cnc.qr.common.exception;

public class ResultMessagesNotificationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instance of {@link ResultMessages}.
     */
    private final ResultMessages resultMessages;

    /**
     * Single argument constructor.
     * @param messages instance of {@link ResultMessages}
     */
    protected ResultMessagesNotificationException(ResultMessages messages) {
        this(messages, null);
    }

    /**
     * Two argument constructor.
     * @param messages instance of {@link ResultMessages}
     * @param cause {@link Throwable} instance
     */
    public ResultMessagesNotificationException(ResultMessages messages, Throwable cause) {
        super(cause);
        if (messages == null) {
            throw new IllegalArgumentException("messages must not be null");
        }
        this.resultMessages = messages;
    }

    /**
     * Returns the messages in String format.
     * @return String messages
     */
    @Override
    public String getMessage() {
        return resultMessages.toString();
    }

    /**
     * Returns the {@link ResultMessages} instance.
     * @return {@link ResultMessages} instance
     */
    public ResultMessages getResultMessages() {
        return resultMessages;
    }
}
