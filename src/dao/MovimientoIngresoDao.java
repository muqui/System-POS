/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import org.hibernate.Session;
import pojo.Ingreso;

/**
 *
 * @author mq12-pc
 */
public class MovimientoIngresoDao {
    public void altaIngreso(Ingreso ingreso, Session session) throws Exception {


           
            session.save(ingreso);
           
      

    }
}
