package com.strong.tea.entity;

import com.strong.tea.annotation.Column;
import com.strong.tea.annotation.Id;
import com.strong.tea.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "network_interface")
public class NetworkInterface {
    @Id
    @Column
    private long id;

    @Column
    private String name;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "ipv4_address")
    private String ipv4Address;

    @Column(name = "ipv6_address")
    private String ipv6Address;

    @Column(name = "gateway_address")
    private String gatewayAddress;

    @Column(name = "device_id")
    private Integer deviceId;

}
