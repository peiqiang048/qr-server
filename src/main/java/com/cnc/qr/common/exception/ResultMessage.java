package com.cnc.qr.common.exception;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class ResultMessage implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * emtpy array object.
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * message code.
     */
    private final String code;

    /**
     * message arguments.
     */
    private final Object[] args;

    /**
     * message text.
     */
    private final String text;

    /**
     * Constructor.<br>
     *
     * @param code message code
     * @param args replacement values of message format
     * @param text default message
     */
    public ResultMessage(String code, Object[] args, String text) {
        this.code = code;
        this.args = args == null ? EMPTY_ARRAY : args;
        this.text = text;
    }

    /**
     * create <code>ResultMessage</code>. instance which has the given code and args<br>
     * <p>
     * <code>text</code> is <code>null</code>
     * </p>
     *
     * @param code message code (must not be null)
     * @param args replacement values of message format
     * @return ResultMessage instance
     */
    public static ResultMessage fromCode(String code, Object... args) {
        return new ResultMessage(code, args, null);
    }

    /**
     * create <code>ResultMessage</code>. instance which has the given text<br>
     * <p>
     * <code>code</code> is <code>null</code>
     * </p>
     *
     * @param text message tet (must not be null)
     * @return ResultMessage instance
     */
    public static ResultMessage fromText(String text) {
        return new ResultMessage(null, EMPTY_ARRAY, text);

    }

    /**
     * returns code.
     *
     * @return code (<code>null</code> if message have a text)
     */
    public String getCode() {
        return code;
    }

    /**
     * returns args.
     *
     * @return args (not <code>null</code>)
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * returns text.
     *
     * @return text (<code>null</code> if message have a code)
     */
    public String getText() {
        return text;
    }

    /**
     * returns the hash code.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    /**
     * Returns whether the {@code obj} is equal to current instance of {@code ResultMessage}.
     *
     * <p>
     * Returns true if: <br>
     * <ul>
     * <li>the obj is the same instance as the current one. OR</li>
     * <li>if code AND text of the two instances are same respectively (including null values).</li>
     * </ul>
     * </p>
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResultMessage other = (ResultMessage) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (!Arrays.equals(args, other.args)) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }

    /**
     * Outputs code and text in the {@code toString()} method of {@code ResultMessage}.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ResultMessage [code=" + code + ", args=" + Arrays.toString(args)
            + ", text=" + text + "]";
    }

    /**
     * special handling for the serialization and deserialization process.
     *
     * @param out ObjectOutputStream
     * @throws IOException {@link java.io.ObjectOutputStream#defaultWriteObject()}
     * @see java.io.Serializable
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * special handling for the serialization and deserialization process.
     *
     * @param in ObjectInputStream
     * @throws IOException            {@link java.io.ObjectInputStream#defaultReadObject()}
     * @throws ClassNotFoundException {@link java.io.ObjectInputStream#defaultReadObject()}
     * @see java.io.Serializable
     */
    private void readObject(
        ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
