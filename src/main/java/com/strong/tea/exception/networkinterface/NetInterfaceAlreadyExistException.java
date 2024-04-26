package com.strong.tea.exception.networkinterface;

import com.strong.tea.exception.ClientRequestException;

public class NetInterfaceAlreadyExistException extends ClientRequestException {
    public NetInterfaceAlreadyExistException(String message) {
        super(message);
    }
}
