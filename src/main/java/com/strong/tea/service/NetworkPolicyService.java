package com.strong.tea.service;

import com.strong.tea.dto.NetworkPolicyDto;
import com.strong.tea.entity.NetworkPolicy;
import com.strong.tea.exception.networkpolicy.NetPolicyAlreadyExistException;
import com.strong.tea.exception.networkpolicy.NetPolicyInvalidIDException;
import com.strong.tea.exception.networkpolicy.NetPolicyNotFoundException;
import com.strong.tea.mapper.NetworkPolicyMapper;
import com.strong.tea.repository.NetworkPolicyRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetworkPolicyService {

    private final NetworkPolicyRepository repository = new NetworkPolicyRepository();


    public NetworkPolicy save(NetworkPolicy netPolicy) throws SQLException,
            NetPolicyAlreadyExistException, NetPolicyInvalidIDException {

        if (netPolicy.getId() == 0) {
            throw new NetPolicyInvalidIDException("Invalid Network Policy ID");
        }
        if (repository.findById(netPolicy.getId()) != null) {
            throw new NetPolicyAlreadyExistException("Network Policy with such ID already exists");
        }
        return repository.save(netPolicy);
    }


    public NetworkPolicy update(NetworkPolicy netPolicy) throws SQLException, NetPolicyInvalidIDException {
        if (netPolicy.getId() == 0 || repository.findById(netPolicy.getId()) == null) {
            throw new NetPolicyInvalidIDException("Invalid Network Policy ID");
        }
        return repository.update(netPolicy);
    }


    public NetworkPolicyDto getNetPolicyById(long id) throws SQLException, NetPolicyNotFoundException {
        NetworkPolicy netPolicy = repository.findById(id);
        if (netPolicy == null) {
            throw new NetPolicyNotFoundException("Network Policy with such ID does not exist");
        }
        return NetworkPolicyMapper.toDto(netPolicy);
    }


    public List<NetworkPolicyDto> getAllNetPolicies() throws SQLException, NetPolicyNotFoundException {
        List<NetworkPolicy> netPolicies = repository.findAll();
        if (netPolicies.isEmpty()) {
            throw new NetPolicyNotFoundException("No Network Policies found");
        }
        List<NetworkPolicyDto> dtos = new ArrayList<>();
        for (NetworkPolicy netPolicy : netPolicies) {
            dtos.add(NetworkPolicyMapper.toDto(netPolicy));
        }
        return dtos;
    }


    public void deleteNetPolicyById(long id) throws SQLException, NetPolicyNotFoundException {
        if (repository.findById(id) == null) {
            throw new NetPolicyNotFoundException("No Network Policies found");
        }
        repository.deleteById(id);
    }
}
