package edu.ufrj.renanbasilio.tempoclimanet.mvc.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Classe que representa um modelo de observações. Este modelo reflete
 * como as informações estão armazenadas no banco de dados.
 * @author Renan Basilio
 */
public class ModeloObservacao {
    private String datahoraobs = "00/00/00 00:00:00";
    private float altondas = 0.0F;
    private float tempagua = 0.0F;
    private Bandeira bandeira = Bandeira.unkn;
    private JsonObject modeloJSON;
    
    public Boolean isLoaded() {
        return !datahoraobs.equals("00/00/00 00:00:00");
    }
    
    /**
     * Esta enumeração define a tradução das bandeiras do serviço de
     * guarda-vidas utilizadas da representação com 4 letras armazenada na base
     * de dados a um nome de cor, descrição e cor no padrão css.
     */
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

        /**
         * Inicializa uma bandeira.
         * @param nome O nome da cor da bandeira.
         * @param desc O significado da banderia (para tooltip).
         * @param cor A cor em notação válida de css da bandeira.
         */
        private Bandeira(String nome, String desc, String cor) {
            this.nome = nome;
            this.desc = desc;
            this.cor = cor;
        }

        /**
         * Recupera o nome da cor da bandeira.
         * @return O nome da cor da bandeira.
         */
        public String getNome() {
           return this.nome;
        }

        /**
         * Recupera a cor em notação válida para css da bandeira.
         * @return A cor da bandeira.
         */
        public String getCor() {
            return this.cor;
        }

        /**
         * Recupera a descrição do significado da bandeira.
         * @return O significado da bandeira.
         */
        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * Recupera a data e hora desta observação, formatada como uma string no
     * formato 'dd/MM/yyyy HH:mm:ss'. 
     * @return A string de data e hora da observação.
     */
    public String getDatahoraobs() {
        return datahoraobs;
    }

    /**
     * Seta a data e hora desta observação, formatada como uma string no formato
     * 'dd/MM/yyyy HH:mm:ss'.
     * @param datahora Um objeto de java.util.Date com a data a ser formatada.
     */
    public void setDatahoraobs(java.util.Date datahora) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.datahoraobs = formatter.format(datahora);
    }

    /**
     * Recupera a altura das ondas no momento desta observação.
     * @return A altura das ondas.
     */
    public float getAltondas() {
        return altondas;
    }

    /**
     * Seta a altura das ondas no momento desta observação.
     * @param altura A altura das ondas.
     */
    public void setAltondas(float altura) {
        this.altondas = altura;
    }

    /**
     * Recupera a temperatura da água no momento desta observação.
     * @return A temperatura da água.
     */
    public float getTempagua() {
        return tempagua;
    }

    /**
     * Seta a temperatura da água no momento desta observação.
     * @param temperatura A temperatura da água.
     */
    public void setTempagua(float temperatura) {
        this.tempagua = temperatura;
    }
    
    /**
     * Recupera a bandeira do serviço de guarda-vidas no momento desta 
     * observação.
     * @return A banderia do serviço de guarda-vidas.
     */
    public Bandeira getBandeira() {
        return bandeira;
    }
    
    /**
     * Seta a banderia do serviço de guarda-vidas no momento desta observação.
     * @param bandeira A bandeira do serviço de guarda-vidas.
     */
    public void setBandeira (Bandeira bandeira) {
        this.bandeira = bandeira;
    }

     /**
     * Cria um ModeloObservacao a partir dos dados mais recentes em relação a uma
     * data que estejam armazenados no banco de dados.
     * @param conn A conexão com a base de dados.
     * @param data A data desejada.
     * @return O ModeloObservacao referente a esta data.
     */
    public static ModeloObservacao fromDB(Connection conn, String data) {
        try {            
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao <= ? "
                    + "ORDER BY datahoraobservacao DESC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
                       
            return ModeloObservacao.fromResultSet(queryResult);
        } catch (Exception e) {
            System.out.println("Failed to load from DB: " + e);
            return new ModeloObservacao();
        }
    }
    
    /**
     * Recupera um ModelObservacao anterior à data fornecida.
     * Se esta for a última data da tabela, retorna o ModeloObservacao referente
     * à própria.
     * @param conn A conexão com a base de dados.
     * @param data A data de referência.
     * @return O ModeloObservacao imediatamente anterior a esta.
     */
    public static ModeloObservacao prevFromDB(Connection conn, String data) {
        try {
            /**
             * As decisões de design referentes a este statement estão
             * discutidas no método equivalente do ModeloMedição.
             */
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao < ? "
                    + "ORDER BY datahoraobservacao DESC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
            
            return ModeloObservacao.fromResultSet(queryResult);
            
        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloObservacao();
        }        
    }
    
    /**
     * Recupera um ModeloObservacao posterior à data fornecida.
     * Se esta for a data mais recente da tabela, retorna o ModeloObservacao 
     * referente à própria.
     * @param conn A conexão com a base de dados.
     * @param data A data de referência.
     * @return O ModeloObservacao imediatamente anterior a esta.
     */
    public static ModeloObservacao nextFromDB(Connection conn, String data) {
        try {
            /**
             * As decisões de design referentes a este statement estão
             * discutidas no método equivalente do ModeloMedição.
             */
            PreparedStatement statement = conn.prepareStatement( 
                    "SELECT * FROM observacoes "
                    + "WHERE datahoraobservacao > ? "
                    + "ORDER BY datahoraobservacao ASC "
                    + "LIMIT 1;");
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date utildate = formatter.parse(data);

            statement.setTimestamp(1, new java.sql.Timestamp(utildate.getTime()));
            ResultSet queryResult = statement.executeQuery();
            
            return ModeloObservacao.fromResultSet(queryResult);

        } catch (Exception e) {
            System.out.println("Failed to prepare SQL statement: " + e);
            return new ModeloObservacao();
            
        }
    }
    
    /**
     * Cria um ModeloObservacao utilizando o resultado de uma query executada 
     * previamente.
     * Este método existe para evitar a repetição de código nas funções de
     * busca em banco de dados; sua existência garante maior manutenabilidade do
     * código pois alterações na forma de se inicializar um ModeloObservacao 
     * podem ser feitas unicamente nesta.
     * @param queryResult Os resultados da query.
     * @return O ModeloObservacao construído a partir deste ResultSet.
     */
    private static ModeloObservacao fromResultSet(ResultSet queryResult) {
        ModeloObservacao observacoes = new ModeloObservacao();
        try {
            /**
             * As decisões de design referentes a este loop estão discutidas no 
             * método equivalente do ModeloMedição.
             */
            while (queryResult.next()) {
                java.util.Date utildate = new java.util.Date(queryResult.getTimestamp("datahoraobservacao").getTime());
                
                observacoes.setDatahoraobs(utildate);
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
    
    /**
     * Constroi um objeto JSON baseado neste modelo e o retorna.
     * @return O objeto JSON criado.
     */
    public JsonObject toJSON() {
        /**
         * O objeto JSON é armazenado em uma variável interna de forma que não
         * precisa ser construído novamente caso este método venha a ser chamado
         * multiplas vezes sobre o mesmo objeto.
         */
        if(modeloJSON != null) return modeloJSON;
        
        modeloJSON = Json.createObjectBuilder()
                .add("datahoraobs", datahoraobs)
                .add("altondas", altondas)
                .add("tempagua", tempagua)
                .add("bandeira", 
                        Json.createObjectBuilder()
                        .add("nome", bandeira.getNome())
                        .add("desc", bandeira.getDesc())
                        .add("cor", bandeira.getCor())
                        .build()
                        )
                .build();
        
        return modeloJSON;
    }
}