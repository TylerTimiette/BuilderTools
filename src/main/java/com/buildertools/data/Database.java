package com.buildertools.data;

import com.buildertools.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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

    protected abstract Connection getSQLConnection();

    public abstract void load();

    void initialize() {
        this.connection = this.getSQLConnection();

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM `players` LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            this.close(preparedStatement, resultSet);
        } catch (SQLException var3) {
            this.instance.getLogger().log(Level.SEVERE, "The plugin was not able to make a connection to the database.", var3);
        }

    }

    //Getting number of regions in this world.
    public int numberOfRegions(World world) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int rowcount = 0;
        try {
            this.connection = this.getSQLConnection();
            preparedStatement = this.connection.prepareStatement("SELECT * from `regions` WHERE `world`=" + world.getName() + ";");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rowcount++;
            }

        } catch (SQLException e) {
            this.instance.getLogger().log(Level.SEVERE, "There has been an error executing the statement.", e);
        } finally {
            this.close(preparedStatement, resultSet);
            this.instance.getLogger().info("Row count is currently " + rowcount + " for region # in world " + world.getName());
        }
        return rowcount;
    }


    public PlayerData getLastCMDBlock(UUID uuid) {
        PlayerData playerData = new PlayerData(0, 0, 0, null, uuid);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            this.connection = this.getSQLConnection();
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
            this.connection = this.getSQLConnection();
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
            this.close(preparedStatement, (ResultSet) null);
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

    public void setRegion(String world, int size, int xboundary, int yboundary, int zboundary, Location start) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            this.connection = this.getSQLConnection();
//CREATE TABLE IF NOT EXISTS regions (`world` varchar NOT NULL, `number` int NOT NULL, `size` NOT NULL, `x-boundary`, int NOT NULL, `y-boundary` int NOT NULL, `z-boundary` int NOT NULL, `start-x` int NOT NULL, `start-y` int NOT NULL, `start-z` int NOT NULL, `note` varchar NOT NULL, PRIMARY KEY(`world`));
            preparedStatement = this.connection.prepareStatement("INSERT OR REPLACE INTO `regions` (world, number, size, x-boundary, y-boundary, z-boundary, start-x, start-y, start-z, note) VALUES (?,?,?,?,?,?,?,?,?,?);");
            //World name
            preparedStatement.setString(1, world);
            //ID no. in this world
            preparedStatement.setInt(2, numberOfRegions(Bukkit.getWorld(world)) + 1);
            //Size, leaving as 0 rn
            preparedStatement.setInt(3, 0);
            //X-Boundary
            preparedStatement.setInt(4, xboundary);
            //Y-Boundary
            preparedStatement.setInt(5, yboundary);
            //Z-Boundary
            preparedStatement.setInt(6, zboundary);
            //Starting position of region X
            preparedStatement.setInt(7, start.getBlockX());
            //Starting position of region Y
            preparedStatement.setInt(8, start.getBlockY());
            //Starting position of region Z
            preparedStatement.setInt(9, start.getBlockZ());
            //A potential note ID.
            preparedStatement.setString(10, "No notes.");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            this.instance.getLogger().log(Level.SEVERE, "A SQL Statement or ResultSet could not be closed.", e);
        }
        finally {
            this.close(preparedStatement, resultSet);
        }
    }
}
