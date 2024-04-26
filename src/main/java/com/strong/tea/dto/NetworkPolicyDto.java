package com.strong.tea.dto;

import lombok.Data;

@Data
public class NetworkPolicyDto {
    private long network_interface_id;
    private String policy_name;
    private String network_interface_name;
    private String policy_description;
    private String policy_action;
    private String mac_address;
    private String ipv4_address;
    private String ipv6_address;
    private String gateway_address;
}
