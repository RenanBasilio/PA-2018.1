package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;

import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class ModeloObservacao {
    private String datahoraobs = "";
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
            System.out.println(statement);
            ResultSet queryResult = statement.executeQuery();
            
            if (queryResult.next()) {
                formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
                utildate.setTime(queryResult.getTimestamp("datahoraobservacao").getTime());
                
                observacoes.setDatahoraobs(formatter.format(utildate));
                observacoes.setAltondas(queryResult.getFloat("alturaondas"));
                observacoes.setTempagua(queryResult.getFloat("temperaturaagua"));
                
                Bandeira band = Bandeira.valueOf(queryResult.getString("bandeira"));
                observacoes.setBandeira(band);
            }
        } catch (Exception e) {
            System.out.println("Failed to load from DB: " + e);
        }
        return observacoes;
    }
}
