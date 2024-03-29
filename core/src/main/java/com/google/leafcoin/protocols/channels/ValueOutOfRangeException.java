package com.google.leafcoin.protocols.channels;

/**
 * Used when a given value is either too large too afford or too small for the network to accept.
 */
public class ValueOutOfRangeException extends Exception {
    public ValueOutOfRangeException(String message) {
        super(message);
    }
}
