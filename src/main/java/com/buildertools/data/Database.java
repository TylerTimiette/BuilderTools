package com.buildertools.data;

import com.buildertools.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public abstract class Database {
    final Main instance;
    Connection connection;

    Database(Main instance) {
        this.instance = instance;
    }

    protected abstract Connection getCMDSQLConnection();

    public abstract void load();

    void initialize() {
        this.connection = this.getCMDSQLConnection();

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM `players` LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            this.close(preparedStatement, resultSet);
        } catch (SQLException var3) {
            this.instance.getLogger().log(Level.SEVERE, "The plugin was not able to make a connection to the database.", var3);
        }

    }

    public PlayerData getLastCMDBlock(UUID uuid) {
        PlayerData playerData = new PlayerData(0, 0, 0, null, uuid);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            this.connection = this.getCMDSQLConnection();
            preparedStatement = this.connection.prepareStatement("SELECT * FROM `players` WHERE `uuid`=?;");
            preparedStatement.setString(1, uuid.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                playerData.setX(Integer.parseInt(resultSet.getString("x")));
                playerData.setY(Integer.parseInt(resultSet.getString("y")));
                playerData.setZ(Integer.parseInt(resultSet.getString("z")));
                playerData.setWorld(resultSet.getString("world"));
            }

            return playerData;
        } catch (SQLException var10) {
            this.instance.getLogger().log(Level.SEVERE, "There has been an error executing the statement.", var10);
        } finally {
            this.close(preparedStatement, resultSet);
        }

        return playerData;
    }

    public void setLastCMDBlock(PlayerData playerData) {
        PreparedStatement preparedStatement = null;

        try {
            this.connection = this.getCMDSQLConnection();
            preparedStatement = this.connection.prepareStatement("INSERT OR REPLACE INTO `players` (uuid, x, y, z, world) VALUES (?,?,?,?,?);");
            preparedStatement.setString(1, playerData.getUuid().toString());
            preparedStatement.setInt(2, playerData.getLastCMDBlock().getBlockX());
            preparedStatement.setInt(3, playerData.getLastCMDBlock().getBlockY());
            preparedStatement.setInt(4, playerData.getLastCMDBlock().getBlockZ());
            preparedStatement.setString(5, playerData.getLastCMDBlock().getWorld().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException var7) {
            this.instance.getLogger().log(Level.SEVERE, "There has been an error executing the statement.", var7);
        } finally {
            this.close(preparedStatement, (ResultSet)null);
        }

    }

    private void close(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException var4) {
            this.instance.getLogger().log(Level.SEVERE, "A SQL Statement or ResultSet could not be closed.", var4);
        }

    }
}
