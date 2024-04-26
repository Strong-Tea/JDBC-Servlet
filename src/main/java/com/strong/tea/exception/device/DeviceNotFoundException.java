package com.strong.tea.exception.device;

import com.strong.tea.exception.ClientRequestException;

public class DeviceNotFoundException extends ClientRequestException {
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
