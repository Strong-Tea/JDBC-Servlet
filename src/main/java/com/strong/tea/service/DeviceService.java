package com.strong.tea.service;

import com.strong.tea.entity.Device;
import com.strong.tea.exception.device.DeviceAlreadyExistException;
import com.strong.tea.exception.device.DeviceIDMissingException;
import com.strong.tea.exception.device.DeviceNotFoundException;
import com.strong.tea.repository.DeviceRepository;

import java.sql.SQLException;
import java.util.List;

public class DeviceService {

    private final DeviceRepository repository = new DeviceRepository();


    public Device save(Device device) throws SQLException, DeviceAlreadyExistException {
        if (repository.findById(device.getId()) != null) {
            throw new DeviceAlreadyExistException("Device with such ID already exists");
        }
        return repository.save(device);
    }


    public Device update(Device device) throws SQLException, DeviceIDMissingException {
        if (device.getId() == 0) {
            throw new DeviceIDMissingException("The device ID is missing");
        }
        return repository.update(device);
    }


    public Device getDeviceById(long id) throws SQLException, DeviceNotFoundException {
        Device device = repository.findById(id);
        if (device == null) {
            throw new DeviceNotFoundException("Device with such ID does not exist");
        }
        return device;
    }


    public List<Device> getAllDevices() throws SQLException, DeviceNotFoundException {
        List<Device> devices = repository.findAll();
        if (devices.isEmpty()) {
            throw new DeviceNotFoundException("No devices found");
        }
        return devices;
    }


    public void deleteDeviceById(long id) throws SQLException, DeviceNotFoundException {
        if (repository.findById(id) == null) {
            throw new DeviceNotFoundException("Device with such ID does not exist");
        }
        repository.deleteById(id);
    }
}
