/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernateUtil.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Usuario;

/**
 *
 * @author mq12-pc
 */
public class LoginDao {

    Session session = null;
     Transaction transaccion;

    public Usuario verificarDatos(String usuario, String password) throws Exception {
        Usuario u = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaccion = session.beginTransaction();
            String hql = "FROM Usuario WHERE nombre = '" + usuario
                    + "' and password = '" + password + "'";
            Query query = session.createQuery(hql);
            if (!query.list().isEmpty()) {
                u = (Usuario) query.list().get(0);
            }
            transaccion.commit();
           
        } catch (Exception e) {
            throw e;
        }finally{
         session.close();
        }
        return u;
    }

}
