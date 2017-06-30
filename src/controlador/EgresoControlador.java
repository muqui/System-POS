/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.logica.Fechas;
import dao.MovimientoEgresoDao;
import dao.MovimientoEgresoOingresoDao;
import dao.MovimientoIngresoDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Egreso;
import pojo.Movimiento;
import vista.JInternalFrameEgreso;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class EgresoControlador implements ActionListener {

    Session session;
    Transaction transaction;
    private List<Movimiento> tiposMovimientos;
    Principal principal;
    Egreso egreso;
    JInternalFrameEgreso egresoGui;
    MovimientoEgresoOingresoDao movimientoEgresoOingresoDao;
    MovimientoIngresoDao ingresoDao;
    MovimientoEgresoDao egresoDao;

    public EgresoControlador(Principal principal, Egreso egreso, JInternalFrameEgreso egresoGui) {
        this.principal = principal;
        this.egreso = egreso;
        this.egresoGui = egresoGui;
        egresoDao = new MovimientoEgresoDao();
        movimientoEgresoOingresoDao = new MovimientoEgresoOingresoDao();
        this.principal.jMenuItemEgreso.addActionListener(this);
        this.egresoGui.jButtonGuardar.addActionListener(this);
        this.egresoGui.jButtonAyer.addActionListener(this);
        this.principal.jButtonEgreso.addActionListener(this);
    }

    public void iniciar() {
        llenarCombo();
    }

    public void llenarCombo() {
        egresoGui.jComboBoxCategorias.setModel(new DefaultComboBoxModel());
//        altaProducto.getjComboBoxCategorias().setModel(new DefaultComboBoxModel());

        System.out.println("llenar combo");
        List<Movimiento> movimientos;
        movimientos = getTiposMovimientos();
        for (int i = 0; i < movimientos.size(); i++) {
            System.out.println("" + movimientos.get(i).getNombre() + " id " + movimientos.get(i).getId());
            egresoGui.jComboBoxCategorias.addItem("" + movimientos.get(i).getNombre());

        }

    }

    public List<Movimiento> getTiposMovimientos() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            tiposMovimientos = movimientoEgresoOingresoDao.getTipoMovimiento(session, "egreso");
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
        return tiposMovimientos;
    }

    public void ejecutar() {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            egresoDao.altaEgreso(egreso, session);
            JOptionPane.showMessageDialog(null, "Movimiento registrado. ", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            this.transaction.commit();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemEgreso || e.getSource() == principal.jButtonEgreso) {
            iniciar();
            mostrar();
        }
        if (e.getSource() == egresoGui.jButtonGuardar) {
            if (validar() == true) {
                ejecutar();
            }
            System.out.println("Fecha " + egreso.getFecha());
        }
      if(e.getSource() == egresoGui.jButtonAyer){
          egresoGui.jDateChooserFecha.setDate( new Fechas().ayer());
     
      }  
    }
     public void mostrar() {
         egresoGui.jDateChooserFecha.setDate(new Date());
        if (egresoGui.isVisible()) {
            egresoGui.toFront();
          

        } else {
            principal.jDesktopPane.add(egresoGui);
            egresoGui.pack();
            egresoGui.setVisible(true);
           
        }
        
    }
public void limpiar(){
   egresoGui.txtCantidad.setText("");
   egresoGui.jTextAreaDescripcion.setText("");
   egresoGui.jDateChooserFecha.setDate(null);
}
    public boolean validar() {
        boolean bandera = true;
        egreso.setFecha(egresoGui.jDateChooserFecha.getDate());
        if ("".equals(egresoGui.txtCantidad.getText().trim()) || "".endsWith(egresoGui.jTextAreaDescripcion.getText().trim()) || egreso.getFecha() == null) {
            System.out.println("bandera falso");
            bandera = false;
            JOptionPane.showMessageDialog(null, "Capture todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("bandera verdadero");
            egreso.setUsuario(principal.usuario);
            egreso.setNombre("" + egresoGui.jComboBoxCategorias.getSelectedItem());

            egreso.setDescripcion(egresoGui.jTextAreaDescripcion.getText().trim());
            egreso.setCantidad(new BigDecimal(egresoGui.txtCantidad.getText().trim()));
            egreso.setFechaMovimiento(new Date());
        }
        return bandera;
    }
}
