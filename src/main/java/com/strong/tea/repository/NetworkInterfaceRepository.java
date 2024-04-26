package com.strong.tea.repository;

import com.strong.tea.entity.NetworkInterface;

public class NetworkInterfaceRepository extends AbstractRepo<NetworkInterface, Long> {

    public NetworkInterfaceRepository() {
        super(NetworkInterface.class);
    }
}
