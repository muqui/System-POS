/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import org.hibernate.Session;
import pojo.Categoria;

/**
 *
 * @author mq12-pc
 */
public class CrearCategoriaDao {
      public void addCategoria(Categoria categoria, Session session) throws Exception {
        session.save(categoria);
    }
}
