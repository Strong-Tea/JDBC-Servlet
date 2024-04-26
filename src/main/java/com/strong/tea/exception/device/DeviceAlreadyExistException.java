package com.strong.tea.exception.device;

import com.strong.tea.exception.ClientRequestException;

public class DeviceAlreadyExistException extends ClientRequestException {
    public DeviceAlreadyExistException(String message) {
        super(message);
    }
}
