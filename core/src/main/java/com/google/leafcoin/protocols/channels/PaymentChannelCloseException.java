package com.google.leafcoin.protocols.channels;

/**
 * Used to indicate that a channel was closed before it was expected to be closed.
 * This could mean the connection timed out, the other send sent an error or a CLOSE message, etc
 */
public class PaymentChannelCloseException extends Exception {
    public enum CloseReason {
        /** We could not find a version which was mutually acceptable with the client/server */
        NO_ACCEPTABLE_VERSION,
        /** Generated by a client when the server attempted to lock in our funds for an unacceptably long time */
        TIME_WINDOW_TOO_LARGE,
        /** Generated by a client when the server requested we lock up an unacceptably high value */
        SERVER_REQUESTED_TOO_MUCH_VALUE,

        // Values after here indicate its probably possible to try reopening channel again

        /**
         * <p>The {@link com.google.leafcoin.protocols.channels.PaymentChannelClient#close()} method was called or the
         * client sent a CLOSE message.</p>
         * <p>As long as the server received the CLOSE message, this means that the channel was closed and the payment
         * transaction (if any) was broadcast. If the client attempts to open a new connection, a new channel will have
         * to be opened.</p>
         */
        CLIENT_REQUESTED_CLOSE,

        /**
         * <p>The {@link com.google.leafcoin.protocols.channels.PaymentChannelServer#close()} method was called or server
         * sent a CLOSE message.</p>
         *
         * <p>This may occur if the server opts to close the connection for some reason, or automatically if the channel
         * times out (called by {@link StoredPaymentChannelServerStates}).</p>
         *
         * <p>For a client, this usually indicates that we should try again if we need to continue paying (either
         * opening a new channel or continuing with the same one depending on the server's preference)</p>
         */
        SERVER_REQUESTED_CLOSE,

        /** Remote side sent an ERROR message */
        REMOTE_SENT_ERROR,
        /** Remote side sent a message we did not understand */
        REMOTE_SENT_INVALID_MESSAGE,

        /** The connection was closed without an ERROR/CLOSE message */
        CONNECTION_CLOSED,
    }

    CloseReason error;
    public CloseReason getCloseReason() {
        return error;
    }

    public PaymentChannelCloseException(String message, CloseReason error) {
        super(message);
        this.error = error;
    }

    public String toString() {
        return "PaymentChannelCloseException for reason " + getCloseReason().toString();
    }
}
