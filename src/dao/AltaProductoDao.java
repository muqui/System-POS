/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Categoria;
import pojo.Tproducto;

/**
 *
 * @author mq12-pc
 */
public class AltaProductoDao {



    public void altaProducto(Tproducto producto, Session session) throws Exception {           
            session.save(producto);              
    }

     public List<Categoria> getCategorias(Session session) throws Exception {
        
        return session.createCriteria(Categoria.class).list();
    }
public Categoria getCategoria(Session session, String nombre ) throws Exception {
   // String hql = " FROM Categoria where nombre = '"+nombre+"'";
          
String hql="from Categoria where nombre=:nombre";

        Query query=session.createQuery(hql);
      query.setParameter("nombre", nombre);
        
        return (Categoria) query.uniqueResult();
    }
}
