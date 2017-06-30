/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import org.hibernate.Session;
import pojo.Tproducto;

/**
 *
 * @author mq12-pc
 */
public class ModificarProductoDao {
      public void modificarProducto(Tproducto producto, Session session) throws Exception {        
            session.update(producto);
    }
}
