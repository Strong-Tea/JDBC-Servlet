package com.strong.tea.exception.networkinterface;

import com.strong.tea.exception.ClientRequestException;

public class NetInterfaceInvalidIDException extends ClientRequestException {
    public NetInterfaceInvalidIDException(String message) {
        super(message);
    }
}
