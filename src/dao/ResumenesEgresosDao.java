/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Egreso;

/**
 *
 * @author muqui
 */
public class ResumenesEgresosDao {
     public List<Egreso> getDetallesegreso(Session session, Date desde, Date hasta) throws Exception {
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String i = df.format(desde);
        String f = df.format(hasta);

        String hql = " from Egreso where fecha >= '" + i + "' AND fecha <= '" + f + "'";
        System.out.println("hql = " + hql);
        Query query = session.createQuery(hql);

        List<Egreso> egresos = (List<Egreso>)  query.list();
       
        return egresos;
    }
}
