package com.strong.tea.service;


import com.strong.tea.entity.NetworkInterface;


import com.strong.tea.exception.networkinterface.NetInterfaceAlreadyExistException;

import com.strong.tea.exception.networkinterface.NetInterfaceInvalidIDException;
import com.strong.tea.exception.networkinterface.NetInterfaceNotFoundException;
import com.strong.tea.repository.NetworkInterfaceRepository;

import java.sql.SQLException;
import java.util.List;

public class NetworkInterfaceService {

    private final NetworkInterfaceRepository repository = new NetworkInterfaceRepository();


    public NetworkInterface save(NetworkInterface networkInterface) throws SQLException, NetInterfaceAlreadyExistException {
        if (repository.findById(networkInterface.getId()) != null) {
            throw new NetInterfaceAlreadyExistException("Network Interface with such ID already exists");
        }
        return repository.save(networkInterface);
    }


    public NetworkInterface update(NetworkInterface networkInterface) throws SQLException, NetInterfaceInvalidIDException {
        if (networkInterface.getId() == 0) {
            throw new NetInterfaceInvalidIDException("The Network Interface ID is missing");
        }
        return repository.update(networkInterface);
    }


    public NetworkInterface getNetInterfaceById(long id) throws SQLException, NetInterfaceNotFoundException {
        NetworkInterface networkInterface = repository.findById(id);
        if (networkInterface == null) {
            throw new NetInterfaceNotFoundException("Network Interface with such ID does not exist");
        }
        return networkInterface;
    }


    public List<NetworkInterface> getAllNetInterfaces() throws SQLException, NetInterfaceNotFoundException {
        List<NetworkInterface> interfaces = repository.findAll();
        if (interfaces.isEmpty()) {
            throw new NetInterfaceNotFoundException("No Network Interfaces was found");
        }
        return interfaces;
    }


    public void deleteNetInterfaceById(long id) throws SQLException, NetInterfaceNotFoundException {
        if (repository.findById(id) == null) {
            throw new NetInterfaceNotFoundException("No Network Interfaces was found");
        }
        repository.deleteById(id);
    }
}
