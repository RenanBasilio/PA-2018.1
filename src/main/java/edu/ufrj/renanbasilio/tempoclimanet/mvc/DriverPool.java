/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.HashMap;
import org.postgresql.ds.PGConnectionPoolDataSource;
import java.util.Properties;

/**
 *
 * @author renan
 */
public class DriverPool {
    private static PGConnectionPoolDataSource postgresDataSource;
    
    private String serverName = "";
    private int portNumber = 0;
    private String dbName = "";
    private Boolean isInit = false;
    
    private String user = "postgres";
    private String pass = "admin";
    

    public void setServer(String name) {isInit = false; serverName = name;}
    
    public void setPort(int portNumber) {isInit = false; portNumber = portNumber;}
    
    public void setDatabase(String name) {isInit = false; dbName = name;}
    
    public void setUser(String username) {user = username;}
    
    public void setPassword(String password) {pass = password;}
    
    public void setCredentials(String username, String password) {
        setUser(username);
        setPassword(password);
    }
    
    public void initialize() {
        try {
            postgresDataSource = new PGConnectionPoolDataSource();
            postgresDataSource.setServerName(serverName);
            postgresDataSource.setPortNumber(portNumber);
            postgresDataSource.setDatabaseName(dbName);
            isInit = true;
        } catch (Exception e) {
            System.out.println("Failed to initialize Postgres driver pool: " + e);
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        try {
            if(!isInit) {throw new Exception("DriverPool not initialized.");}
            Connection con = postgresDataSource.getConnection("postgres", "admin");
            return con;
        } catch (Exception e) {
            System.out.println("Failed to get connection: " + e);
            return null;
        }
    }
}
