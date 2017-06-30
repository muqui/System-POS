/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Tventadetalle;

/**
 *
 * @author muqui
 */
public class ResumenesVentasDao {
     public List<Tventadetalle> getDetallesventas(Session session, Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = " from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'" +
                " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.tproducto.categoria.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        List<Tventadetalle> ventas = (List<Tventadetalle>) query.list();

        return ventas;
    }
     //Muestra todo lo del dia
      public List<Tventadetalle> getDetallesventas(Session session, Date desde, Date hasta) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = " from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'  ORDER BY  t.tventa.fechaRegistro desc" ;
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        List<Tventadetalle> ventas = (List<Tventadetalle>) query.list();

        return ventas;
    }
      
       public BigDecimal getVentas(Session session, Date desde, Date hasta, String usuario, String categoria) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        //String hql = "select sum(precioVentaTotal) from Tventa  v where fechaRegistro >= '" + i + "00:00:00' AND fechaRegistro <= '" + f + "23:59:59' and v.usuario.id = "+id;
         String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f + "23:59:59'" +
                " AND t.tventa.usuario.nombre LIKE '%" + usuario + "%' AND t.tproducto.categoria.nombre LIKE '%" + categoria + "%' ORDER BY  t.tventa.fechaRegistro desc";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
}
