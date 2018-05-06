package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;
import edu.ufrj.renanbasilio.tempoclimanet.mvc.PoolManager;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ModeloMedicao {

    private String datahoraautom = "";
    private float temperatura = 0.0F;
    private float umidade = 0.0F;
    private float orvalho = 0.0F;
    private float pressao = 0.0F;
    private float precipitacao = 0.0F;
    private float precipacumul = 0.0F;
    private float velvento = 0.0F;
    private float dirvento = 0.0F;
    
    public String getDatahoraautom() {
        return datahoraautom;
    }

    public void setDatahoraautom(String datahora) {
        this.datahoraautom = datahora;
    }
    
    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getUmidade() {
        return umidade;
    }

    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    public float getOrvalho() {
        return orvalho;
    }

    public void setOrvalho(float orvalho) {
        this.orvalho = orvalho;
    }

    public float getPressao() {
        return pressao;
    }

    public void setPressao(float pressao) {
        this.pressao = pressao;
    }

    public float getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(float precipitacao) {
        this.precipitacao = precipitacao;
    }

    public float getPrecipacumul() {
        return precipacumul;
    }

    public void setPrecipacumul(float precipitacao) {
        this.precipacumul = precipitacao;
    }

    public float getVelvento() {
        return velvento;
    }

    public void setVelvento(float velocidade) {
        this.velvento = velocidade;
    }

    public float getDirvento() {
        return dirvento;
    }

    public void setDirvento(float direcao) {
        this.dirvento = direcao;
    }
    
    public static ModeloMedicao fromDB(String data) {
        ModeloMedicao medicoes = new ModeloMedicao();
        try {
            Connection conn = PoolManager.getInstance().getPool("tempoclimanet").getConnection();
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM medidasautomaticas "
                    + "WHERE datahora <= ? "
                    + "ORDER BY datahora DESC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
            
            if (queryResult.next()) {
                formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
                utildate.setTime(queryResult.getTimestamp("datahora").getTime());
                
                medicoes.setDatahoraautom(formatter.format(utildate));
                medicoes.setTemperatura(queryResult.getFloat("temperatura"));
                medicoes.setUmidade(queryResult.getFloat("umidade"));
                medicoes.setOrvalho(queryResult.getFloat("orvalho"));
                medicoes.setPressao(queryResult.getFloat("pressao"));
                medicoes.setPrecipitacao(queryResult.getFloat("taxaprecipitacao"));
                medicoes.setPrecipacumul(queryResult.getFloat("precipitacaoacum"));
                medicoes.setVelvento(queryResult.getFloat("velvento"));
                medicoes.setDirvento(queryResult.getFloat("dirvento"));
            }
        } catch (Exception e) {
            System.out.println("Failed to load from DB: " + e);
        }
        return medicoes;
    }
}
