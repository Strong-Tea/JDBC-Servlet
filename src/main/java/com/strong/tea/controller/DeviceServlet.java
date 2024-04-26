package com.strong.tea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strong.tea.entity.Device;
import com.strong.tea.exception.ClientRequestException;
import com.strong.tea.service.DeviceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/device/*")
public class DeviceServlet extends HttpServlet {

    public  DeviceService service = new DeviceService();
    public  ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                Device device = service.getDeviceById(Long.parseLong(idParam));
                resp.getWriter().write(objectMapper.writeValueAsString(device));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                List<Device> devices = service.getAllDevices();
                resp.getWriter().write(objectMapper.writeValueAsString(devices));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (ClientRequestException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Device device = objectMapper.readValue(req.getReader(), Device.class);
            service.save(device);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ClientRequestException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Device device = objectMapper.readValue(req.getReader(), Device.class);
            service.update(device);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ClientRequestException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            service.deleteDeviceById(Long.parseLong(idParam));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ClientRequestException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }
}
