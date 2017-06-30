/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author mq12-pc
 */
public class ResumenesDao {

    //Total
    public BigDecimal getVentas(Session session, Date desde, Date hasta) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(precioVentaTotal) from Tventa where fechaRegistro >= '" + i + "00:00:00' AND fechaRegistro <= '" + f + "23:59:59'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
    //Por usuario
    public BigDecimal getVentas(Session session, Date desde, Date hasta, int id) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(precioVentaTotal) from Tventa  v where fechaRegistro >= '" + i + "00:00:00' AND fechaRegistro <= '" + f + "23:59:59' and v.usuario.id = "+id;
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
 //Por usuario y moviemiento
    public BigDecimal getVentas(Session session, Date desde, Date hasta, int id , String movimiento) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        //String hql = "select sum(precioVentaTotal) from Tventa  v where fechaRegistro >= '" + i + "00:00:00' AND fechaRegistro <= '" + f 
        //        + "23:59:59' and v.usuario.id = "+id + " and v.tventadetalles.tproducto.categoria =  '" +movimiento+"'";
       String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f 
               + "23:59:59' and t.tventa.usuario.id = "+id + " and t.tproducto.categoria.nombre = '" + movimiento + "'" ;
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }
    // moviemiento
    public BigDecimal getVentas(Session session, Date desde, Date hasta,  String movimiento) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        //String hql = "select sum(precioVentaTotal) from Tventa  v where fechaRegistro >= '" + i + "00:00:00' AND fechaRegistro <= '" + f 
        //        + "23:59:59' and v.usuario.id = "+id + " and v.tventadetalles.tproducto.categoria =  '" +movimiento+"'";
       String hql = "select sum(totalPrecioVenta) from Tventadetalle t where t.tventa.fechaRegistro >= '" + i + "00:00:00' AND t.tventa.fechaRegistro <= '" + f 
               + "23:59:59' and t.tproducto.categoria.nombre = '" + movimiento + "'" ;
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ventas = (BigDecimal) query.uniqueResult();
        if (ventas == null) {
            ventas = new java.math.BigDecimal("0.00");
        }
        return ventas;
    }

    public BigDecimal getIngresos(Session session, Date desde, Date hasta) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Ingreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        return ingresos;
    }
    //por tipo
     public BigDecimal getIngresos(Session session, Date desde, Date hasta, String movimiento) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Ingreso i where fecha >= '" + i + "' AND fecha <= '" + f + "' and i.nombre = '" + movimiento +"'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal ingresos = (BigDecimal) query.uniqueResult();
        if (ingresos == null) {
            ingresos = new java.math.BigDecimal("0.00");
        }
        return ingresos;
    }
//total
        public BigDecimal getEgresos(Session session, Date desde, Date hasta) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal egresos = (BigDecimal) query.uniqueResult();
        if (egresos == null) {
            egresos = new java.math.BigDecimal("0.00");
        }
        return egresos;
    }
        // por tipo 
        public BigDecimal getEgresos(Session session, Date desde, Date hasta, String nombre) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = "select sum(cantidad) from Egreso e where fecha >= '" + i + "' AND fecha <= '" + f + "' and e.nombre = '"+nombre +"'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        BigDecimal egresos = (BigDecimal) query.uniqueResult();
        if (egresos == null) {
            egresos = new java.math.BigDecimal("0.00");
        }
        return egresos;
    }
}
