package com.strong.tea.mapper;

import com.strong.tea.dto.NetworkPolicyDto;
import com.strong.tea.entity.NetworkPolicy;

public class NetworkPolicyMapper {

    public static NetworkPolicy toEntity(NetworkPolicyDto entityDto) {
        NetworkPolicy networkPolicy = new NetworkPolicy();
        networkPolicy.setId(entityDto.getNetwork_interface_id());
        networkPolicy.setName(entityDto.getPolicy_name());
        networkPolicy.setDescription(entityDto.getPolicy_description());
        networkPolicy.setAction(entityDto.getPolicy_action());
        return networkPolicy;
    }

    public static NetworkPolicyDto toDto(NetworkPolicy entity) {
        NetworkPolicyDto networkPolicyDto = new NetworkPolicyDto();
        networkPolicyDto.setNetwork_interface_id(entity.getId());
        networkPolicyDto.setPolicy_name(entity.getName());
        networkPolicyDto.setPolicy_description(entity.getDescription());
        networkPolicyDto.setPolicy_action(entity.getAction());
        networkPolicyDto.setNetwork_interface_name(entity.getNetworkInterface().getName());
        networkPolicyDto.setMac_address(entity.getNetworkInterface().getMacAddress());
        networkPolicyDto.setIpv4_address(entity.getNetworkInterface().getIpv4Address());
        networkPolicyDto.setIpv6_address(entity.getNetworkInterface().getIpv6Address());
        networkPolicyDto.setGateway_address(entity.getNetworkInterface().getGatewayAddress());
        return networkPolicyDto;
    }
}
