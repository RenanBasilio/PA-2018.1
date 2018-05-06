/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import java.sql.Connection;
import org.postgresql.ds.PGConnectionPoolDataSource;

/**
 * Classe que armazena uma Pool de conexões com um banco de dados Postgres.
 * @author Renan Basilio
 */
public class DriverPool {
    private static PGConnectionPoolDataSource postgresDataSource;
    
    private String serverAddress = "localhost";
    private int portNumber = 5432;
    private String dbName = "";
    private Boolean isInit = false;
    
    private String user = "postgres";
    private String pass = "admin";
    
    /**
     * Seta o endereço do servidor ao qual esta pool estará conectada.
     * Deve-se chamar initialize() após esta chamada para efetivar a alteração.
     * @param address O endereço do servidor.
     */
    public void setAddress(String address) {serverAddress = address;}
    
    /**
     * Seta a porta do servidor na qual esta pool estará conectada.
     * Deve-se chamar initialize() após esta chamada para efetivar a alteração.
     * @param number O número da porta.
     */
    public void setPort(int number) {portNumber = number;}
    
    /**
     * Seta em uma única chamada o endereço e porta na qual esta pool estará 
     * conectada.
     * Deve-se chamar initialize() após esta chamada para efetivar a alteração.
     * @param address O endereço do servidor.
     * @param port O número da porta.
     */
    public void setServer(String address, int port) {
        
    }
    
    /**
     * Seta o nome da base de dados na qual esta pool estará conectada.
     * Deve-se chamar initialize() após esta chamada para efetivar a alteração.
     * @param name O nome da base de dados.
     */
    public void setDatabase(String name) {dbName = name;}
    
    /**
     * Seta o nome de usuário a ser utilizado por conexões onde não especificado
     * na chamada.
     * @param username O nome de usuário.
     */
    public void setDefaultUser(String username) {user = username;}
    
    /**
     * Seta a senha a ser utilizada por conexões onde não for especificado na
     * chamada.
     * @param password A senha.
     */
    public void setDefaultPassword(String password) {pass = password;}
    
    /**
     * Seta em uma única chamada as credenciais a serem utilizadas por conexões
     * onde não for especificado na chamada.
     * @param username O nome de usuário.
     * @param password A senha.
     */
    public void setDefaultCredentials(String username, String password) {
        setDefaultUser(username);
        setDefaultPassword(password);
    }
    
    /**
     * Inicializa a pool de conexões.
     * Quando chamado pela primeira vez cria um novo pool e inicializa. Se
     * chamado com uma pool já inicializada seta suas propriedades para o estado
     * atual desta classe.
     */
    public void initialize() {
        try {
            if( !isInit ) {
                postgresDataSource = new PGConnectionPoolDataSource();
                isInit = true;
            }
            postgresDataSource.setServerName(serverAddress);
            postgresDataSource.setPortNumber(portNumber);
            postgresDataSource.setDatabaseName(dbName);
        } catch (Exception e) {
            System.out.println("Failed to initialize Postgres driver pool: " + e);
            e.printStackTrace();
        }
    }
    
    /**
     * Fecha a conexão com o DataSource.
     */
    public void close() {
        postgresDataSource = null;
        isInit = false;
    }
    
    /**
     * Recupera uma conexão com o DataSource representado por este pool.
     * @return A conexão com o DataSource.
     */
    public Connection getConnection() {
        try {
            if(!isInit) {throw new Exception("DriverPool not initialized.");}
            Connection con = postgresDataSource.getConnection(user, pass);
            return con;
        } catch (Exception e) {
            System.out.println("Failed to get connection: " + e);
            return null;
        }
    }
    
    /**
     * Recupera uma conexão com o DataSource representado por este pool
     * utilizando credenciais fornecidas no momento da chamada.
     * @param username O nome de usuário.
     * @param password A senha.
     * @return A conexão com o DataSource.
     */
    public Connection getConnection(String username, String password) {
        try {
            if(!isInit) {throw new Exception("DriverPool not initialized.");}
            Connection con = postgresDataSource.getConnection(username, password);
            return con;
        } catch (Exception e) {
            System.out.println("Failed to get connection: " + e);
            return null;
        }
    }
}
