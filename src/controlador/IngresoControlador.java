/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.logica.Fechas;
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
import pojo.Ingreso;
import pojo.Movimiento;
import vista.JInternalFrameIngreso;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class IngresoControlador implements ActionListener {

    Session session;
    Transaction transaction;
    private List<Movimiento> tiposMovimientos;
    Principal principal;
    Ingreso ingreso;
    JInternalFrameIngreso ingresoGui;
    MovimientoEgresoOingresoDao movimientoEgresoOingresoDao;
    MovimientoIngresoDao ingresoDao;

    public IngresoControlador(Principal principal, Ingreso ingreso, JInternalFrameIngreso ingresoGui) {
        this.principal = principal;
        this.ingreso = ingreso;
        this.ingresoGui = ingresoGui;
        ingresoDao = new MovimientoIngresoDao();
        movimientoEgresoOingresoDao = new MovimientoEgresoOingresoDao();
        this.principal.jMenuItemIngreso.addActionListener(this);
        this.ingresoGui.jButtonGuardar.addActionListener(this);
          this.ingresoGui.jButtonAyer.addActionListener(this);
        this.principal.jButtonIngreso.addActionListener(this);
    }

    public void iniciar() {
        llenarCombo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemIngreso || e.getSource() == principal.jButtonIngreso) {
           iniciar();
           mostrar();
        }
        if (e.getSource() == ingresoGui.jButtonGuardar) {
            if (validar() == true) {
                ingresoM();
            }

        }
         if(e.getSource() == ingresoGui.jButtonAyer){
          ingresoGui.jDateChooserFecha.setDate( new Fechas().ayer());
     
      }  
    }
 public void mostrar() {
     ingresoGui.jDateChooserFecha.setDate(new Date());
        if (ingresoGui.isVisible()) {
            ingresoGui.toFront();
         
        } else {
            principal.jDesktopPane.add(ingresoGui);
            ingresoGui.pack();
            ingresoGui.setVisible(true);
           
        }
        
    }
    public void llenarCombo() {
        ingresoGui.jComboBoxCategorias.setModel(new DefaultComboBoxModel());
//        altaProducto.getjComboBoxCategorias().setModel(new DefaultComboBoxModel());

        System.out.println("llenar combo");
        List<Movimiento> movimientos;
        movimientos = getTiposMovimientos();
        for (int i = 0; i < movimientos.size(); i++) {
            System.out.println("" + movimientos.get(i).getNombre() + " id " + movimientos.get(i).getId());
            ingresoGui.jComboBoxCategorias.addItem("" + movimientos.get(i).getNombre());

        }

    }

    public List<Movimiento> getTiposMovimientos() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            tiposMovimientos = movimientoEgresoOingresoDao.getTipoMovimiento(session, "ingreso");
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

    public void ingresoM() {

        this.session = null;
        this.transaction = null;
        //String fecha = new SimpleDateFormat("yyyy-MM-dd").format(ingresoGui.jDateChooserFecha);
        ingreso.setUsuario(principal.usuario);
        ingreso.setNombre("" + ingresoGui.jComboBoxCategorias.getSelectedItem());
        ingreso.setFecha(ingresoGui.jDateChooserFecha.getDate());
        ingreso.setDescrpcion(ingresoGui.jTextAreaDescripcion.getText().trim());
        ingreso.setCantidad(new BigDecimal(ingresoGui.txtCantidad.getText().trim()));
        ingreso.setFechaMovimiento(new Date());

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            ingresoDao.altaIngreso(ingreso, session);
            limpiar();
            JOptionPane.showMessageDialog(null, "movimiento registrado.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.transaction.commit();

        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex, "Exito", JOptionPane.INFORMATION_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }
           
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    public void limpiar() {
        ingresoGui.txtCantidad.setText("");
        ingresoGui.jTextAreaDescripcion.setText("");
        ingresoGui.jDateChooserFecha.setDate(null);
    }

    public boolean validar() {
        boolean bandera = true;
        ingreso.setFecha(ingresoGui.jDateChooserFecha.getDate());
        if ("".equals(ingresoGui.txtCantidad.getText().trim()) || "".endsWith(ingresoGui.jTextAreaDescripcion.getText().trim()) || ingreso.getFecha() == null) {
            System.out.println("bandera falso");
            bandera = false;
            JOptionPane.showMessageDialog(null, "Capture todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("bandera verdadero");
            ingreso.setUsuario(principal.usuario);
            ingreso.setNombre("" + ingresoGui.jComboBoxCategorias.getSelectedItem());

            ingreso.setDescrpcion(ingresoGui.jTextAreaDescripcion.getText().trim());
            ingreso.setCantidad(new BigDecimal(ingresoGui.txtCantidad.getText().trim()));
            ingreso.setFechaMovimiento(new Date());
        }
        return bandera;
    }
}
