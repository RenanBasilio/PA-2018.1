package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Classe que representa um modelo de medições automáticas. Este modelo reflete
 * como as informações estão armazenadas no banco de dados.
 * @author Renan Basilio
 */
public class ModeloMedicao {

    private String datahoraautom = "00/00/00 00:00:00";
    private float temperatura = 0.0F;
    private float umidade = 0.0F;
    private float orvalho = 0.0F;
    private float pressao = 0.0F;
    private float precipitacao = 0.0F;
    private float precipacumul = 0.0F;
    private float velvento = 0.0F;
    private float dirvento = 0.0F;
    private JsonObject modeloJSON;
    
    public Boolean isLoaded() {
        return !datahoraautom.equals("00/00/00 00:00:00");
    }
    
    /**
     * Recupera a data e hora desta medição automática, formatada como uma
     * string no formato 'dd/MM/yyyy HH:mm:ss'.
     * @return A string de data e hora desta medição. 
     */
    public String getDatahoraautom() {
        return datahoraautom;
    }

    /**
     * Seta a data e hora desta medição automática, formatada como uma string no
     * formato 'dd/MM/yyyy HH:mm:ss'.
     * @param datahora Um objeto de java.util.Date com a data a ser formatada.
     */
    public void setDatahoraautom(java.util.Date datahora) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.datahoraautom = formatter.format(datahora);
    }
    
    /**
     * Recupera a temperatura no momento desta medição automática.
     * @return A temperatura.
     */
    public float getTemperatura() {
        return temperatura;
    }

    /**
     * Seta a temperatura no momento desta medição automática.
     * @param temperatura A temperatura.
     */
    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Recupera a umidade relativa do ar no momento desta medição automática.
     * @return A umidade relativa do ar.
     */
    public float getUmidade() {
        return umidade;
    }

    /**
     * Seta a umidade relativa do ar no momento desta medição automática.
     * @param umidade A umidade relativa do ar.
     */
    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    /**
     * Recupera o ponto de orvalho no momento desta medição automática. 
     * @return O ponto de orvalho.
     */
    public float getOrvalho() {
        return orvalho;
    }

    /**
     * Seta o ponto de orvalho no momento desta medição automática.
     * @param orvalho O ponto de orvalho.
     */
    public void setOrvalho(float orvalho) {
        this.orvalho = orvalho;
    }

    /**
     * Recupera a pressão atmosférica no momento desta medição automática.
     * @return A pressão atmosférica.
     */
    public float getPressao() {
        return pressao;
    }

    /**
     * Seta a pressão atmosférica no momento desta medição automática.
     * @param pressao A pressão atmosférica.
     */
    public void setPressao(float pressao) {
        this.pressao = pressao;
    }

    /**
     * Recupera o volume de precipitação acumulado entre esta medição automática
     * e a medição anterior.
     * @return O volume de precipitação.
     */
    public float getPrecipitacao() {
        return precipitacao;
    }

    /**
     * Seta o volume de precipitação acumulado entre esta medição automática e a
     * medição anterior.
     * @param precipitacao O volume de precipitção. 
     */
    public void setPrecipitacao(float precipitacao) {
        this.precipitacao = precipitacao;
    }

    /**
     * Recupera o volume de precipitação acumulado nos últimos (30 minutos?) do
     * momento desta medição automática.
     * @return O volume de precipitação acumulado.
     */
    public float getPrecipacumul() {
        return precipacumul;
    }

    /**
     * Seta o volume de precipitação acumulado nos últimos (30 minutos?) do
     * momento desta medição automática.
     * @param precipitacao O volume de precipitação acumulado.
     */
    public void setPrecipacumul(float precipitacao) {
        this.precipacumul = precipitacao;
    }

    /**
     * Recupera a velocidade do vento no momento desta medição automática.
     * @return A velocidade do vento.
     */
    public float getVelvento() {
        return velvento;
    }

    /**
     * Seta a velocidade do vento no momento desta medição automática.
     * @param velocidade A velocidade do vento.
     */
    public void setVelvento(float velocidade) {
        this.velvento = velocidade;
    }

    /**
     * Recupera a direção do vento no momento desta medição automática. A
     * direção é armazenada como um ângulo em graus.
     * @return A direção do vento, em graus.
     */
    public float getDirvento() {
        return dirvento;
    }

    /**
     * Seta a direção do vento no momento desta medição automática. A direção
     * deve ser armazenada como um ângulo em graus.
     * @param direcao A direção do vento.
     */
    public void setDirvento(float direcao) {
        this.dirvento = direcao;
    }
    
    /**
     * Cria um ModeloMedição a partir dos dados mais recentes em relação a uma
     * data que estejam armazenados no banco de dados.
     * @param conn A conexão com a base de dados.
     * @param data A data desejada.
     * @return O ModeloMedição referente a esta data.
     */
    public static ModeloMedicao fromDB(Connection conn, String data) {
        try {
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM medidasautomaticas "
                    + "WHERE datahora <= ? "
                    + "ORDER BY datahora DESC "
                    + "LIMIT 1;");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
            
            return ModeloMedicao.fromResultSet(queryResult);

        } catch (Exception e) {
            System.out.println("Failed to load from DB: " + e); 
        }
        return new ModeloMedicao();
    }
    
    /**
     * Recupera um ModeloMedição anterior à data fornecida.
     * Se esta for a última data da tabela, retorna o ModeloMedição referente à 
     * própria.
     * @param conn A conexão com a base de dados.
     * @param data A data de referência.
     * @return O ModeloMedição imediatamente anterior a esta.
     */
    public static ModeloMedicao prevFromDB(Connection conn, String data) {
        try {
            /**
             * Critério reconsiderado. Inicialmente, foi considerado que seria
             * ineficiente realizar duas consultas casos os elementos sejam o
             * primeiro ou o último da base de dados. No entanto, para o
             * carregamento de elementos anteriores o elemento ser o primeiro da
             * base de dados caracteriza um pior caso. Assim, torna-se mais
             * eficiente realizar duas consultas separadas.
             */
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM medidasautomaticas "
                    + "WHERE datahora < ? "
                    + "ORDER BY datahora DESC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
                        
            return ModeloMedicao.fromResultSet(queryResult);
            
        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloMedicao();
        }        
    }
    
     /**
     * Recupera um ModeloMedição posterior à data fornecida.
     * Se esta for a data mais recente da tabela, retorna o ModeloMedição 
     * referente à própria.
     * @param conn A conexão com a base de dados.
     * @param data A data de referência.
     * @return O ModeloMedição imediatamente anterior a esta.
     */
    public static ModeloMedicao nextFromDB(Connection conn, String data) {
        try {
            /**
             * Critério reconsiderado. Para facilitar a execução de queries
             * futuras a partir do uso de chamadas AJAX, que será a forma
             * primária de execução, torna-se mais vantajoso executar duas
             * consultas no caso menos frequente de noscript.
             */
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM medidasautomaticas "
                    + "WHERE datahora > ? "
                    + "ORDER BY datahora ASC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
                        
            return ModeloMedicao.fromResultSet(queryResult);

        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloMedicao();
            
        }
    }
    
    /**
     * Cria um ModeloMedicao utilizando o resultado de uma query executada 
     * previamente.
     * Este método existe para evitar a repetição de código nas funções de
     * carregamento busca em banco de dados; sua existência garante maior
     * manutenabilidade do código pois alterações na forma de se inicializar
     * um ModeloMedição podem ser feitas unicamente nesta.
     * @param queryResult Os resultados da query.
     * @return O ModeloMedicao construído a partir deste ResultSet.
     */
    private static ModeloMedicao fromResultSet(ResultSet queryResult) {
        ModeloMedicao medicoes = new ModeloMedicao();
        try {
            
            /**
             * Idealmente, poderia ser utilizado queryResult.last() para
             * carregar diretamente o último resultado da query, que é aquele no
             * qual estamos diretamente interessados. No entanto, tal chamada
             * depende da Scrollability do ResultSet, e resultou em uma excessão
             * com as configurações atuais.
             * Como tratam-se de apenas dois resultados, por enquanto esta
             * solução basta, porém posteriormente seria preferível conseguir um
             * ResultSet sobre o qual a chamada seja possível.
             */
            while (queryResult.next()) {
                java.util.Date utildate = new java.util.Date(queryResult.getTimestamp("datahora").getTime());
                
                medicoes.setDatahoraautom(utildate);
                medicoes.setTemperatura(queryResult.getFloat("temperatura"));
                medicoes.setUmidade(queryResult.getFloat("umidade"));
                medicoes.setOrvalho(queryResult.getFloat("orvalho"));
                medicoes.setPressao(queryResult.getFloat("pressao"));
                medicoes.setPrecipitacao(queryResult.getFloat("taxaprecipitacao"));
                medicoes.setPrecipacumul(queryResult.getFloat("precipitacaoacum"));
                medicoes.setVelvento(queryResult.getFloat("velvento"));
                medicoes.setDirvento(queryResult.getFloat("dirvento"));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to load from DB: " + ex);
        }
        return medicoes;
    }
    
    /**
     * Constroi um objeto JSON baseado neste modelo e o retorna.
     * @return O objeto JSON criado.
     */
    public JsonObject toJSON() {
        if(!isLoaded()) return JsonObject.EMPTY_JSON_OBJECT;
        
        /**
         * O objeto JSON é armazenado em uma variável interna de forma que não
         * precisa ser construído novamente caso este método venha a ser chamado
         * multiplas vezes sobre o mesmo objeto.
         */
        if(modeloJSON != null) return modeloJSON;
        
        modeloJSON = Json.createObjectBuilder()
                .add("datahoraautom", datahoraautom)
                .add("temperatura", temperatura)
                .add("umidade", umidade)
                .add("orvalho", orvalho)
                .add("pressao", pressao)
                .add("precipitacao", precipitacao)
                .add("precipacumul", precipacumul)
                .add("velvento", velvento)
                .add("dirvento", dirvento)
                .build();
        
        return modeloJSON;
    }
}
