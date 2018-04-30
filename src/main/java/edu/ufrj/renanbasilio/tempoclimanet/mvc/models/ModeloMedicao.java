package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;

public class ModeloMedicao {

    private String datahoraautom = "";
    private float temperatura = 0.0F;
    private float umidade = 0.0F;
    private float orvalho = 0.0F;
    private float pressao = 0.0F;
    private float precipitacao = 0.0F;
    private float precipacumul = 0.0F;
    private float velvento = 0.0F;
    private String dirvento = "";
    
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

    public String getDirvento() {
        return dirvento;
    }

    public void setDirvento(String direcao) {
        this.dirvento = direcao;
    }
}
