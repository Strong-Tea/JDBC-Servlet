package com.strong.tea.exception.networkinterface;

import com.strong.tea.exception.ClientRequestException;

public class NetInterfaceNotFoundException extends ClientRequestException {
    public NetInterfaceNotFoundException(String message) {
        super(message);
    }
}
