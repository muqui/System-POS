/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import pojo.Tproducto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Usuario;

/**
 *
 * @author mq12
 */
public class DaoTProducto {

    Session session = null;
    Transaction transaccion;

    public List<Tproducto> getAll(Session session) throws Exception {

        return session.createCriteria(Tproducto.class).list();
    }

    public Tproducto getByCodigoBarras(Session session, String codigoBarras) throws Exception {
        String hql = "from Tproducto where codigoBarras=:codigoBarras";
        Query query = session.createQuery(hql);
        query.setParameter("codigoBarras", codigoBarras);

        return (Tproducto) query.uniqueResult();
    }

    public List<Tproducto> getPorNombre(Session session, String nombre) throws Exception {

        String hql = "from Tproducto where nombre LIKE :nombre";
        Query query = session.createQuery(hql);
        query.setParameter("nombre", "%" + nombre + "%");
        List<Tproducto> productosPorNombre = (List<Tproducto>) query.list();

        return productosPorNombre;
    }

    public List<Tproducto> getPapeleria(Session session) throws Exception {

        String hql = "From Tproducto as t  where t.categoria.id = 5";
        Query query = session.createQuery(hql);

        List<Tproducto> papeleria = (List<Tproducto>) query.list();

        return papeleria;
    }

    public BigDecimal totalPapeleria(Session session) {
        String hql = "select sum(t.cantidad * t.precioVentaUnitario ) from Tproducto as t  where t.categoria.id = 5";
        Query query = session.createQuery(hql);
        BigDecimal total = (BigDecimal) query.uniqueResult();
        return total;

    }
    
    public BigDecimal totalPapeleriaProveedor(Session session) {
        String hql = "select sum(t.cantidad * t.precioProveedor ) from Tproducto as t  where t.categoria.id = 5";
        Query query = session.createQuery(hql);
        BigDecimal total = (BigDecimal) query.uniqueResult();
        return total;

    }
}
