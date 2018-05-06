/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import java.util.HashMap;

/**
 * Singleton que gerencia todas as pools de conexão utilizadas pelo servidor.
 * @author Renan Basilio
 */
public class PoolManager {
    private static PoolManager instance = null;
    private HashMap<String, DriverPool> pools = new HashMap<String, DriverPool>();;
    
    private PoolManager() {};
    
    /**
     * Recupera a instância global do PoolManager.
     * @return A instância global do PoolManager.
     */
    public static PoolManager getInstance() {
        if (instance == null) { instance = new PoolManager(); }
        return instance;
    };
    
    /**
     * Adiciona uma DriverPool ao PoolManager.
     * @param name O nome a ser mapeado ao novo DriverPool.
     * @return O DriverPool recém criado.
     */
    public DriverPool addPool(String name) {
        DriverPool pool = new DriverPool();
        pools.put(name, pool);
        return pool;
    }
    
    /**
     * Recupera a DriverPool referente a um nome especificado.
     * @param name O nome com que a DriverPool foi registrada.
     * @return A DriverPool referente a este nome.
     */
    public DriverPool getPool(String name) {
        return pools.get(name);
    }
    
    /**
     * Remove a DriverPool  referente a um nome especificado do PoolManager.
     * A DriverPool não é deletada por esta chamada de função. Assim, as
     * conexões abertas não serão fechadas por esta chamada, e a DriverPool
     * deixará de existir quando a última referência à mesma for removida.
     * @param name O nome da DriverPool a ser removida.
     */
    public void removePool(String name) {
        pools.remove(name);
    }
}
