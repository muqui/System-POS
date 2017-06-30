/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.VentasDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Tventadetalle;
import vista.JInternalFrameMisVentas;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class MisVentasControlador implements ActionListener {
    Session session;
    Transaction transaction;
    private List<Tventadetalle> ventas;
    private VentasDao ventasDao;
   Principal principal;
    JInternalFrameMisVentas misVentas;
    public MisVentasControlador(Principal principal, JInternalFrameMisVentas misVentas) {
        this.principal = principal;
        this.misVentas = misVentas;
        ventasDao = new VentasDao();
        this.principal.jMenuItemMisVentas.addActionListener(this);
        this.principal.jButtonMisVEntas.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
if(e.getSource() == principal.jMenuItemMisVentas || e.getSource() == principal.jButtonMisVEntas){
   ventas = getVentas();
       misVentas.jTableMisVentas.setModel(llenarTabla());
   mostrar();
   
 }

    }
   public void mostrar() {
        if (misVentas.isVisible()) {
            misVentas.toFront();
          
        } else {
            principal.jDesktopPane.add(misVentas);
            misVentas.pack();
            misVentas.setVisible(true);
           
        }
        
    }  
     public List<Tventadetalle> getVentas() {
         this.session = null;
        this.transaction = null;
        try {
           
             this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            int idUsuario = principal.usuario.getId();
            ventas = ventasDao.getVentas(session, idUsuario);
          
              this.transaction.commit();
        } catch (Exception ex) {
             if (this.transaction != null) {
                transaction.rollback();
            }

          
        }
        finally {
            if (this.session != null) {
                //this.session.close(); //Comentado para poder mostrar fecha.
            }
        }
        return ventas;
    }
     
     public DefaultTableModel llenarTabla() {
         
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO UNITARIO", "CANTIDAD", "FECHA", "TOTAL"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < ventas.size(); i++) {

//fila[0] = productos.get(i).getId();
           fila[0] = ventas.get(i).getCodigoBarrasProducto();
            fila[1] = ventas.get(i).getNombreProducto();
            fila[2] = ventas.get(i).getPrecioVentaUnitarioProducto();
            fila[3] = ventas.get(i).getCantidad();
            fila[4] = ventas.get(i).getTventa().getIdVenta();
            fila[5] = ventas.get(i).getTotalPrecioVenta();
             tableModel.addRow(fila);
            System.out.println(" " + ventas.get(i).getCodigoBarrasProducto());

        }
        return tableModel;
    }

    public void iniciar() {
       
    }
}
