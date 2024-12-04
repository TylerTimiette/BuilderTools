package com.buildertools.data;

import com.buildertools.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    public SQLite(Main instance) {
        super(instance);
    }

    public Connection getCMDSQLConnection() {
        File cmdBlocks = new File(this.instance.getDataFolder(), "cmdblocks.db");

        if (!cmdBlocks.exists()) {
            try {
                if (!cmdBlocks.createNewFile()) {
                    throw new IOException("Could not create File. Probably due to read/write permissions.");
                }
            } catch (IOException var5) {
                this.instance.getLogger().log(Level.SEVERE, "File write error: players.db", var5);
            }
        }

        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return this.connection;
            }

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + cmdBlocks);
            return this.connection;
        } catch (SQLException var3) {
            this.instance.getLogger().log(Level.SEVERE, "SQLite exception on initialize", var3);
        } catch (ClassNotFoundException var4) {
            this.instance.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }

        return null;
    }

    public void load() {
        this.connection = this.getCMDSQLConnection();

        try {
            Statement s = this.connection.createStatement();
            String SQLiteCreatePlayerTable = "CREATE TABLE IF NOT EXISTS players (`uuid` varchar NOT NULL,`x` int NOT NULL,`y` int NOT NULL,`z` int NOT NULL,`world` varchar NOT NULL,UNIQUE(uuid));";
            s.executeUpdate(SQLiteCreatePlayerTable);
            s.close();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        this.initialize();
    }
}
