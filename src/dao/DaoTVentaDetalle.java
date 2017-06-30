/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;


import pojo.Tventadetalle;
import org.hibernate.Session;

/**
 *
 * @author KevinArnold
 */
public class DaoTVentaDetalle {

    public boolean insert(Session session, Tventadetalle tVentaDetalle) throws Exception {
        session.save(tVentaDetalle);
        
        return true;
    }
    
}
