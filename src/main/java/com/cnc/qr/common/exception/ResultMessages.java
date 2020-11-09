package com.cnc.qr.common.exception;

import static com.cnc.qr.common.exception.ResultMessageType.DANGER;
import static com.cnc.qr.common.exception.ResultMessageType.ERROR;
import static com.cnc.qr.common.exception.ResultMessageType.INFO;
import static com.cnc.qr.common.exception.ResultMessageType.SUCCESS;
import static com.cnc.qr.common.exception.ResultMessageType.WARNING;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ResultMessages implements Serializable, Iterable<ResultMessage> {

    private static final long serialVersionUID = -1L;

    /**
     * message type.
     */
    private final ResultMessageType type;

    /**
     * message list.
     */
    private final List<ResultMessage> list = new ArrayList<>();

    /**
     * default attribute name for ResultMessages.
     */
    public static final String DEFAULT_MESSAGES_ATTRIBUTE_NAME = StringUtils
        .uncapitalize(ResultMessages.class.getSimpleName());

    /**
     * Constructor.
     * @param type message type
     */
    public ResultMessages(ResultMessageType type) {
        this(type, (ResultMessage[]) null);
    }

    /**
     * Constructor.
     * @param type message type
     * @param messages messages to add
     */
    public ResultMessages(ResultMessageType type, ResultMessage... messages) {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null!");
        }
        this.type = type;
        if (messages != null) {
            addAll(messages);
        }
    }

    /**
     * returns type.
     * @return type
     */
    public ResultMessageType getType() {
        return type;
    }

    /**
     * returns messages.
     * @return messages
     */
    public List<ResultMessage> getList() {
        return list;
    }

    /**
     * add a ResultMessage.
     * @param message ResultMessage instance
     * @return this result messages
     */
    public ResultMessages add(ResultMessage message) {
        if (message != null) {
            this.list.add(message);
        } else {
            throw new IllegalArgumentException("message must not be null");
        }
        return this;
    }

    /**
     * add code to create and add ResultMessages.
     * @param code message code
     * @return this result messages
     */
    public ResultMessages add(String code) {
        if (code != null) {
            this.add(ResultMessage.fromCode(code));
        } else {
            throw new IllegalArgumentException("code must not be null");
        }
        return this;
    }

    /**
     * add code and args to create and add ResultMessages.
     * @param code message code
     * @param args replacement values of message format
     * @return this result messages
     */
    public ResultMessages add(String code, Object... args) {
        if (code != null) {
            this.add(ResultMessage.fromCode(code, args));
        } else {
            throw new IllegalArgumentException("code must not be null");
        }
        return this;
    }

    /**
     * add all messages (excludes <code>null</code> message)<br>.
     * <p>
     * if <code>messages</code> is <code>null</code>, no message is added.
     * </p>
     * @param messages messages to add
     * @return this messages
     */
    public ResultMessages addAll(ResultMessage... messages) {
        if (messages != null) {
            for (ResultMessage message : messages) {
                add(message);
            }
        } else {
            throw new IllegalArgumentException("messages must not be null");
        }
        return this;
    }

    /**
     * add all messages (excludes <code>null</code> message).<br>
     * <p>
     * if <code>messages</code> is <code>null</code>, no message is added.
     * </p>
     * @param messages messages to add
     * @return this messages
     */
    public ResultMessages addAll(Collection<ResultMessage> messages) {
        if (messages != null) {
            for (ResultMessage message : messages) {
                add(message);
            }
        } else {
            throw new IllegalArgumentException("messages must not be null");
        }
        return this;
    }

    /**
     * returns whether messages are not empty.
     * @return whether messages are not empty
     */
    public boolean isNotEmpty() {
        return !list.isEmpty();
    }

    /**
     * factory method for success messages.
     * @return success messages
     */
    public static ResultMessages success() {
        return new ResultMessages(SUCCESS);
    }

    /**
     * factory method for info messages.
     * @return info messages
     */
    public static ResultMessages info() {
        return new ResultMessages(INFO);
    }

    /**
     * factory method for warning messages.
     * @return warning messages
     * @since 5.0.0
     */
    public static ResultMessages warning() {
        return new ResultMessages(WARNING);
    }

    /**
     * factory method for error messages.
     * @return error messages
     */
    public static ResultMessages error() {
        return new ResultMessages(ERROR);
    }

    /**
     * factory method for danger messages.
     * @return error messages
     */
    public static ResultMessages danger() {
        return new ResultMessages(DANGER);
    }

    /**.
     * Returns {@link Iterator} instance that iterates over a list of {@link ResultMessage}
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<ResultMessage> iterator() {
        return list.iterator();
    }

    /**.
     * Outputs type of messages in this {@code ResultMessages} and the list of messages itself
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ResultMessages [type=" + type + ", list=" + list + "]";
    }

    /**
     * special handling for the serialization and deserialization process.
     * @param out ObjectOutputStream
     * @throws IOException {@link java.io.ObjectOutputStream#defaultWriteObject()}
     * @see java.io.Serializable
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * special handling for the serialization and deserialization process.
     * @param in ObjectInputStream
     * @throws IOException {@link java.io.ObjectInputStream#defaultReadObject()}
     * @throws ClassNotFoundException {@link java.io.ObjectInputStream#defaultReadObject()}
     * @see java.io.Serializable
     */
    private void readObject(
        ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
