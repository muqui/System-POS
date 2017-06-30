/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernateUtil.HibernateUtil;
import java.util.List;
import org.hibernate.*;
import org.hibernate.Session;
import pojo.Usuario;

/**
 *
 * @author mq12
 */
public class ModificarUsuarioDao {
  Transaction transaccion;
    public List<Usuario> getUsuarios(Session session) {
       return session.createCriteria(Usuario.class).list();
    }

    public void actualizar(Usuario usuario, Session session) {
        session.update(usuario);
    }
//     public Usuario usuario(String usuario,Session session) throws Exception {
//        Usuario u = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            transaccion = session.beginTransaction();
//            String hql = "FROM Usuario WHERE nombre = '" + usuario
//                    + "'";
//            Query query = session.createQuery(hql);
//            if (!query.list().isEmpty()) {
//                u = (Usuario) query.list().get(0);
//            }
//            transaccion.commit();
//           
//        } catch (Exception e) {
//            throw e;
//        }finally{
//         session.close();
//        }
//        return u;
//    }
  
}
