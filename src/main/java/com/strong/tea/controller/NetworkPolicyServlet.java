package com.strong.tea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strong.tea.dto.NetworkPolicyDto;
import com.strong.tea.entity.NetworkPolicy;
import com.strong.tea.exception.ClientRequestException;
import com.strong.tea.exception.networkpolicy.NetPolicyNotFoundException;
import com.strong.tea.service.NetworkPolicyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/policy/*")
public class NetworkPolicyServlet extends HttpServlet {

    private final NetworkPolicyService service = new NetworkPolicyService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                NetworkPolicyDto netPolicy = service.getNetPolicyById(Long.parseLong(idParam));
                resp.getWriter().write(objectMapper.writeValueAsString(netPolicy));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                List<NetworkPolicyDto> netPolicies = service.getAllNetPolicies();
                resp.getWriter().write(objectMapper.writeValueAsString(netPolicies));
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
            NetworkPolicy netPolicy = objectMapper.readValue(req.getReader(), NetworkPolicy.class);
            service.save(netPolicy);
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
            NetworkPolicy netPolicy = objectMapper.readValue(req.getReader(), NetworkPolicy.class);
            service.update(netPolicy);
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
            service.deleteNetPolicyById(Long.parseLong(idParam));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NetPolicyNotFoundException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(e.getMessage());
        }
    }
}
