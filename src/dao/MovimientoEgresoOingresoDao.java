/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Movimiento;

/**
 *
 * @author mq12-pc
 */
public class MovimientoEgresoOingresoDao {
     public List<Movimiento> getTipoMovimiento(Session session, String tipo  ) throws Exception {
//       String hql = "from Miquiniela where quiniela.id ="+ quiniela ;
       // String hql = "from Miquiniela m where quiniela.id ="+ tipo +" order by m.aciertos desc";
        String hql =   "From Movimiento where tipo = '"+ tipo +"'";
        Query query = session.createQuery(hql);

        List<Movimiento> movimiento = (List<Movimiento>) query.list();

        return movimiento;
    }
}
