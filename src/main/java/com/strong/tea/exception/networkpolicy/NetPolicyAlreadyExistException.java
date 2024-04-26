package com.strong.tea.exception.networkpolicy;

import com.strong.tea.exception.ClientRequestException;

public class NetPolicyAlreadyExistException extends ClientRequestException {
    public NetPolicyAlreadyExistException(String message) {
        super(message);
    }
}
