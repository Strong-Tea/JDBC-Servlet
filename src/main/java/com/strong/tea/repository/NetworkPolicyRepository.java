package com.strong.tea.repository;

import com.strong.tea.database.DatabaseConnection;
import com.strong.tea.database.DatabaseUtils;
import com.strong.tea.entity.NetworkInterface;
import com.strong.tea.entity.NetworkPolicy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetworkPolicyRepository extends AbstractRepo<NetworkPolicy, Long> {

    public NetworkPolicyRepository() {
        super(NetworkPolicy.class);
    }


    @Override
    public NetworkPolicy save(NetworkPolicy entity) throws SQLException {
        String sql = DatabaseUtils.generateInsertQuery(entity, true);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();
        }
        return entity;
    }


    /**
     * Retrieves a network policy from the database by its unique identifier.
     *
     * @param id The unique identifier of the network policy to retrieve.
     * @return The network policy with the specified ID, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public NetworkPolicy findById(Long id) throws SQLException {
        String sql = """
            SELECT *
            FROM network_policy
            JOIN network_interface ON network_policy.id = network_interface.id
            WHERE network_policy.id = ?;
            """;

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return (mapResultSetToEntity(resultSet));
            }
        }
        return null;
    }



    /**
     * Retrieves all network policies stored in the database.
     *
     * @return A list of network policies retrieved from the database.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<NetworkPolicy> findAll() throws SQLException {

        String sql = """
            SELECT *
            FROM network_policy
            JOIN network_interface ON network_policy.id = network_interface.id;
            """;

        List<NetworkPolicy> networkPolicies = null;

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            networkPolicies = new ArrayList<>();
            while (resultSet.next()) {
                networkPolicies.add(mapResultSetToEntity(resultSet));
            }
        }
        return networkPolicies;
    }


    /**
     * Maps a ResultSet to a NetworkPolicy entity.
     *
     * @param resultSet The ResultSet containing the data to map.
     * @return A NetworkPolicy entity populated with data from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private NetworkPolicy mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        NetworkPolicy networkPolicy = new NetworkPolicy();
        NetworkInterface networkInterface = new NetworkInterface();
        networkPolicy.setNetworkInterface(networkInterface);
        networkPolicy.setId(resultSet.getInt("id"));
        networkPolicy.setName(resultSet.getString("name"));
        networkPolicy.setDescription(resultSet.getString("description"));
        networkPolicy.setAction(resultSet.getString("action"));
        networkPolicy.getNetworkInterface().setName(resultSet.getString(6));
        networkPolicy.getNetworkInterface().setMacAddress(resultSet.getString("mac_address"));
        networkPolicy.getNetworkInterface().setIpv4Address(resultSet.getString("ipv4_address"));
        networkPolicy.getNetworkInterface().setIpv6Address(resultSet.getString("ipv6_address"));
        networkPolicy.getNetworkInterface().setGatewayAddress(resultSet.getString("gateway_address"));
        networkPolicy.getNetworkInterface().setDeviceId(resultSet.getInt("device_id"));
        return networkPolicy;
    }
}
