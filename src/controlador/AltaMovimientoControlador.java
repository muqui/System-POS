/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.CrearMovimientoDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Movimiento;
import vista.JInternalFrameCrearMovimiento;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class AltaMovimientoControlador implements ActionListener{
     Session session;
    Transaction transaction;
    Movimiento movimiento;
    Principal principal;
    JInternalFrameCrearMovimiento crearMovimiento;
    CrearMovimientoDao crearMovimientoDao;
    public AltaMovimientoControlador(Principal principal,  Movimiento movimiento, JInternalFrameCrearMovimiento crearMovimiento) {
        this.principal = principal;
        this.movimiento = movimiento;
        this.crearMovimiento = crearMovimiento;
        crearMovimientoDao = new CrearMovimientoDao();
        this.principal.jMenuItemCrearMovimiento.addActionListener(this);
        this.crearMovimiento.jButtonGuardar.addActionListener(this);
        this.principal.jButtonCrearMovimiento.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == principal.jMenuItemCrearMovimiento || e.getSource() == principal.jButtonCrearMovimiento){
            mostrar();
        }
        if(e.getSource() == crearMovimiento.jButtonGuardar){
            if(crearMovimiento.txtNombre.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null, "Capture nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                crearMovimiento();
            JOptionPane.showMessageDialog(null, "movimiento registrado.", "Exito", JOptionPane.INFORMATION_MESSAGE);
           
            }
        }
    }
     public void mostrar() {
        if (crearMovimiento.isVisible()) {
            crearMovimiento.toFront();
         

        } else {
            principal.jDesktopPane.add(crearMovimiento);
            crearMovimiento.pack();
            crearMovimiento.setVisible(true);
           
        }
        
    }
    public void crearMovimiento(){
        movimiento.setNombre(crearMovimiento.txtNombre.getText().trim());
        movimiento.setTipo(""+crearMovimiento.jComboBoxTipoMovimiento.getSelectedItem());
 this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
           crearMovimientoDao.altaProducto(movimiento, session);
            crearMovimiento.txtNombre.setText("");
            this.transaction.commit();

        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
  

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        System.out.println("moviiento");
}
}
