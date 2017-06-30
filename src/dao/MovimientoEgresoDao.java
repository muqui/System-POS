/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import org.hibernate.Session;
import pojo.Egreso;

/**
 *
 * @author mq12-pc
 */
public class MovimientoEgresoDao {
      public void altaEgreso(Egreso egreso, Session session) throws Exception {


           
            session.save(egreso);
           
      

    }
}
