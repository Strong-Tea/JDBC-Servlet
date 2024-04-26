package com.strong.tea.exception.device;

import com.strong.tea.exception.ClientRequestException;

public class DeviceIDMissingException extends ClientRequestException {
    public DeviceIDMissingException(String message) {
        super(message);
    }
}
