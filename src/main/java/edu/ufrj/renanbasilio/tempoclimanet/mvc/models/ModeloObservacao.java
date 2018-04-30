package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;

public class ModeloObservacao {
    private String datahoraobs = "";
    private float altondas = 0.0F;
    private int tempagua = 0;
    private String bandsalvavidas = "verde";

    public String getDataHoraObs() {
        return datahoraobs;
    }

    public void setDataHoraObs(String datahora) {
        this.datahoraobs = datahora;
    }

    public float getAlturaOndas() {
        return altondas;
    }

    public void setAlturaOndas(float altura) {
        this.altondas = altura;
    }

    public int getTemperaturaAgua() {
        return tempagua;
    }

    public void setTemperaturaAgua(int temperatura) {
        this.tempagua = temperatura;
    }

    public String getBandeiraSalvaVidas() {
        return bandsalvavidas;
    }

    public void setBandeiraSalvaVidas(String cor) {
        this.bandsalvavidas = cor;
    }    
}
