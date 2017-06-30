/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Tventadetalle;

/**
 *
 * @author mq12-pc
 */
public class VentasDao {

    public List<Tventadetalle> getVentas(Session session, int id) throws Exception {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
//       String hql = "from Miquiniela where quiniela.id ="+ quiniela ;
        // String hql = "from Miquiniela m where quiniela.id ="+ tipo +" order by m.aciertos desc";
        // String hql =   "From Tventadetalle ";
       // String hql = "From Tventadetalle as t where t.tventa.usuario.id  = " + id +" and t.tventa.fechaRegistro between '" + today +"' and ' "+ today +"'" 
                ;
        String hql = "From Tventadetalle as t where t.tventa.usuario.id  = " + id +" and   DATE(t.tventa.fechaRegistro) = CURDATE()" ;
          
        Query query = session.createQuery(hql);

        List<Tventadetalle> ventas = (List<Tventadetalle>) query.list();

        return ventas;
    }
}
