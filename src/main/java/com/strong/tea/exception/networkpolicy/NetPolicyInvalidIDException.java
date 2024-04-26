package com.strong.tea.exception.networkpolicy;

import com.strong.tea.exception.ClientRequestException;

public class NetPolicyInvalidIDException extends ClientRequestException {
    public NetPolicyInvalidIDException(String message) {
        super(message);
    }
}
