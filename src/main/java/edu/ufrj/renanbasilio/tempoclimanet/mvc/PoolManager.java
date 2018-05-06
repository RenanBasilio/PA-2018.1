/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufrj.renanbasilio.tempoclimanet.mvc;

import java.util.HashMap;

/**
 *
 * @author renan
 */
public class PoolManager {
    private static PoolManager instance = null;
    private HashMap<String, DriverPool> pools = new HashMap<String, DriverPool>();;
    
    private PoolManager() {};
    
    public static PoolManager getInstance() {
        if (instance == null) { instance = new PoolManager(); }
        return instance;
    };
    
    public DriverPool addPool(String name) {
        DriverPool pool = new DriverPool();
        pools.put(name, pool);
        return pool;
    }
    
    public DriverPool getPool(String name) {
        return pools.get(name);
    }
    
    public void removePool(String name) {
        pools.remove(name);
    }
}
