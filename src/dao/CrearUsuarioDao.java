/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import org.hibernate.Session;
import pojo.Usuario;

/**
 *
 * @author mq12-pc
 */
public class CrearUsuarioDao {
    public void altaProducto(Usuario usuario, Session session) throws Exception {


           
            session.save(usuario);
           
      

    }
}
