package com.strong.tea.repository;

import com.strong.tea.entity.Device;

public class DeviceRepository extends AbstractRepo<Device, Long> {

    public DeviceRepository() {
        super(Device.class);
    }

}
