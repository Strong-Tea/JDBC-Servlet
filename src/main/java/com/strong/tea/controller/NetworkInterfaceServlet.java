package com.strong.tea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strong.tea.entity.NetworkInterface;
import com.strong.tea.exception.ClientRequestException;
import com.strong.tea.exception.networkinterface.NetInterfaceNotFoundException;
import com.strong.tea.service.NetworkInterfaceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/interface/*")
public class NetworkInterfaceServlet extends HttpServlet {

    private final NetworkInterfaceService service = new NetworkInterfaceService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                NetworkInterface netInterface = service.getNetInterfaceById(Long.parseLong(idParam));
                resp.getWriter().write(objectMapper.writeValueAsString(netInterface));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                List<NetworkInterface> netInterfaces = service.getAllNetInterfaces();
                resp.getWriter().write(objectMapper.writeValueAsString(netInterfaces));
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
            NetworkInterface netInterface = objectMapper.readValue(req.getReader(), NetworkInterface.class);
            service.save(netInterface);
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
            NetworkInterface netInterface = objectMapper.readValue(req.getReader(), NetworkInterface.class);
            service.update(netInterface);
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
            service.deleteNetInterfaceById(Long.parseLong(idParam));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NetInterfaceNotFoundException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }
}
