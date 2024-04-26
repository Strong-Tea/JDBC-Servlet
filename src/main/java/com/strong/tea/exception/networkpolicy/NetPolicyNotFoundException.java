package com.strong.tea.exception.networkpolicy;

import com.strong.tea.exception.ClientRequestException;

public class NetPolicyNotFoundException extends ClientRequestException {
    public NetPolicyNotFoundException(String message) {
        super(message);
    }
}
