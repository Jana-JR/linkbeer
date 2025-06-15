package org.jana.urlshortnersb.exceptions;

public class BlockedUrlException extends RuntimeException {
    private final String reason;

    public BlockedUrlException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
