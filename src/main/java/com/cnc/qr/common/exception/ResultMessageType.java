package com.cnc.qr.common.exception;

public enum ResultMessageType {

    /**
     * message type is <code>success</code>.
     */
    SUCCESS("success"),
    /**
     * message type is <code>info</code>.
     */
    INFO("info"),
    /**
     * message type is <code>warning</code>.
     * @since 5.0.0
     */
    WARNING("warning"),
    /**
     * message type is <code>error</code>.
     */
    ERROR("error"),
    /**
     * message type is <code>danger</code>.
     */
    DANGER("danger");

    /**
     * message type.
     */
    private final String type;

    /**
     * Create ResultMessageType instance.<br>
     * @param type message type
     */
    private ResultMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    /**
     * <p>
     * returns message type.
     * </p>
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.type;
    }
}
