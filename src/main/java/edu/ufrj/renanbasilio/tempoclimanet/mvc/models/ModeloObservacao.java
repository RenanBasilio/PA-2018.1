package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloObservacao {
    private String datahoraobs = "00/00/00 00:00:00";
    private float altondas = 0.0F;
    private float tempagua = 0.0F;
    private Bandeira bandeira = Bandeira.unkn;
    
    public enum Bandeira {
        verd("Verde", "Mar bom.", "forestgreen"),
        amar("Amarela", "Cuidado com o mar.", "yellow"),
        verm("Vermelha", "Mar perigoso." ,"red"),
        pret("Preta", "Não entre, risco de morte.", "black"),
        azul("Azul", "Pessoa encontrada.", "blue"),
        roxo("Roxo", "Atenção, animais marinhos.", "purple"),
        unkn("Desconhecida", "Não há bandeira disponível.", "gray");

        private final String nome;
        private final String desc;
        private final String cor;

        private Bandeira(String nome, String desc, String cor) {
            this.nome = nome;
            this.desc = desc;
            this.cor = cor;
        }

        public String getNome() {
           return this.nome;
        }

        public String getCor() {
            return this.cor;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public String getDatahoraobs() {
        return datahoraobs;
    }

    public void setDatahoraobs(String datahora) {
        this.datahoraobs = datahora;
    }

    public float getAltondas() {
        return altondas;
    }

    public void setAltondas(float altura) {
        this.altondas = altura;
    }

    public float getTempagua() {
        return tempagua;
    }

    public void setTempagua(float temperatura) {
        this.tempagua = temperatura;
    }
    
    public Bandeira getBandeira() {
        return bandeira;
    }
    
    public void setBandeira (Bandeira bandeira) {
        this.bandeira = bandeira;
    }
    
    public static ModeloObservacao fromDB(String data) {
        ModeloObservacao observacoes = new ModeloObservacao();
        try {
            Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao <= ? "
                    + "ORDER BY datahoraobservacao DESC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            return ModeloObservacao.loadFromDB(statement);
        } catch (Exception e) {
            System.out.println("Failed to load from DB: " + e);
            return new ModeloObservacao();
        }
    }
    
    public static ModeloObservacao prevFromDB(String data) {
        try {
            Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao <= ? "
                    + "ORDER BY datahoraobservacao DESC "
                    + "LIMIT 2;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            
            return ModeloObservacao.loadFromDB(statement);
            
        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloObservacao();
        }        
    }
    
    public static ModeloObservacao nextFromDB(String data) {
        try {
            Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao >= ? "
                    + "ORDER BY datahoraobservacao ASC "
                    + "LIMIT 2;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            
            return ModeloObservacao.loadFromDB(statement);

        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloObservacao();
            
        }
    }
    
    private static ModeloObservacao loadFromDB(PreparedStatement statement) {
        ModeloObservacao observacoes = new ModeloObservacao();
        try {
            ResultSet queryResult = statement.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            while (queryResult.next()) {
                java.util.Date utildate = new java.util.Date(queryResult.getTimestamp("datahoraobservacao").getTime());
                
                observacoes.setDatahoraobs(formatter.format(utildate));
                observacoes.setAltondas(queryResult.getFloat("alturaondas"));
                observacoes.setTempagua(queryResult.getFloat("temperaturaagua"));
                
                Bandeira band = Bandeira.valueOf(queryResult.getString("bandeira"));
                observacoes.setBandeira(band);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to load from DB: " + ex);
        }
        return observacoes;
    }
}