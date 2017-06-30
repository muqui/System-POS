/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.logica.Fechas;
import dao.ResumenesDao;
import dao.ResumenesEgresosDao;
import dao.ResumenesIngresosDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Egreso;
import pojo.Ingreso;
import vista.JInternalFrameIngresEgreso;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class IngresoEgresoControlador implements ActionListener {

    private Fechas fechas;
    private Principal principal;
    private JInternalFrameIngresEgreso ingresoEgresoGui;
    Session session;
    Transaction transaction;
    private Date desde;
    private Date hasta;
    private List<Ingreso> listaingreso;
    private List<Egreso> listaegreso;
    private ResumenesIngresosDao resumenesIngresosDao;
    private ResumenesEgresosDao resumenesEgresosDao;
    private ResumenesDao resumenesDao;
    private BigDecimal ingresos = new java.math.BigDecimal("0.00");
    private BigDecimal egresos = new java.math.BigDecimal("0.00");

    public IngresoEgresoControlador(Principal principal, JInternalFrameIngresEgreso ingresoEgresoGui) {
        this.principal = principal;
        this.ingresoEgresoGui = ingresoEgresoGui;
        resumenesIngresosDao = new ResumenesIngresosDao();
        resumenesEgresosDao = new ResumenesEgresosDao();
        resumenesDao = new ResumenesDao();
        fechas = new Fechas();
        principal.jMenuItemingresosEgresos.addActionListener(this);
        principal.jButtonIngresoEgreso.addActionListener(this);
        ingresoEgresoGui.jButtonMostrar.addActionListener(this);
        ingresoEgresoGui.jButtonHoy.addActionListener(this);
        ingresoEgresoGui.jButtonDiaAnterior.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == principal.jMenuItemingresosEgresos || e.getSource() == principal.jButtonIngresoEgreso) {
            desde = new Date();
            hasta = new Date();
            ingresoEgresoGui.jLabelDate.setText(fechas.fecha(desde));
            Ingresos();
            ingresoEgresoGui.jLabelTotal.setText(totalIngreso());
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaIngreso());
            mostrar();
        }
        if (e.getSource() == ingresoEgresoGui.jButtonMostrar) {
            desde = ingresoEgresoGui.jDateChooserDesde.getDate();
            hasta = ingresoEgresoGui.jDateChooserHasta.getDate();
            ingresoEgreso();
        }
        if (e.getSource() == ingresoEgresoGui.jButtonHoy) {
            Hoy();
        }
        if (e.getSource() == ingresoEgresoGui.jButtonDiaAnterior) {

            diaAnterior();

        }
    }
public void mostrar() {
    ingresoEgresoGui.jDateChooserDesde.setDate(new Date());
     ingresoEgresoGui.jDateChooserHasta.setDate(new Date());
        if (ingresoEgresoGui.isVisible()) {
            ingresoEgresoGui.toFront();
           

        } else {
            principal.jDesktopPane.add(ingresoEgresoGui);
            ingresoEgresoGui.pack();
            ingresoEgresoGui.setVisible(true);
          
        }
        
    }

    public void diaAnterior() {
        desde = fechas.ayer();
        hasta = fechas.ayer();
        if (ingresoEgresoGui.jComboBoxIngresoEgreso.getSelectedItem().toString().equals("ingreso")) {

            Ingresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaIngreso());
            ingresoEgresoGui.jLabelTotal.setText(totalIngreso());
            ingresoEgresoGui.jLabelDate.setText(fechas.fecha(hasta));
        } else {
            egresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaEgreso());
            ingresoEgresoGui.jLabelTotal.setText(totalEgreso());
            ingresoEgresoGui.jLabelDate.setText(fechas.fecha(hasta));
        }

        //ingresoEgresoGui.setVisible(true);
        fechas.ayer--;
    }

    public void ingresoEgreso() {

        if (ingresoEgresoGui.jComboBoxIngresoEgreso.getSelectedItem().toString().equals("ingreso")) {
            Ingresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaIngreso());
            ingresoEgresoGui.jLabelTotal.setText(totalIngreso());
        } else {
            egresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaEgreso());
            ingresoEgresoGui.jLabelTotal.setText(totalEgreso());
        }
        ingresoEgresoGui.jLabelDate.setText(fechas.rangoFecha(desde, hasta));
    }

    public void Hoy() {
        fechas.ayer = -1;
        desde = new Date();
        hasta = new Date();
        if (ingresoEgresoGui.jComboBoxIngresoEgreso.getSelectedItem().toString().equals("ingreso")) {

            Ingresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaIngreso());
            ingresoEgresoGui.jLabelTotal.setText(totalIngreso());

        } else {
            egresos();
            ingresoEgresoGui.jTableIngresoEgreso.setModel(llenarTablaEgreso());
            ingresoEgresoGui.jLabelTotal.setText(totalEgreso());
        }
        ingresoEgresoGui.jLabelDate.setText(fechas.fecha(hasta));

    }

    public String totalIngreso() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            ingresos = resumenesDao.getIngresos(session, desde, hasta);
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        return "" + ingresos;
    }

    public String totalEgreso() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            egresos = resumenesDao.getEgresos(session, desde, hasta);
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        return "" + egresos;
    }

    public DefaultTableModel llenarTablaIngreso() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"FECHA", "MOVIMIENTO", "DESCRIPCION", "CANTIDAD"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaingreso.size(); i++) {

            fila[0] = listaingreso.get(i).getFecha();
            fila[1] = listaingreso.get(i).getNombre();
            fila[2] = listaingreso.get(i).getDescrpcion();
            fila[3] = listaingreso.get(i).getCantidad();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    public DefaultTableModel llenarTablaEgreso() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"FECHA", "MOVIMIENTO", "DESCRIPCION", "CANTIDAD"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < listaegreso.size(); i++) {

            fila[0] = listaegreso.get(i).getFecha();
            fila[1] = listaegreso.get(i).getNombre();
            fila[2] = listaegreso.get(i).getDescripcion();
            fila[3] = listaegreso.get(i).getCantidad();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    public void Ingresos() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            listaingreso = resumenesIngresosDao.getDetallesingreso(session, desde, hasta);
            this.transaction.commit();
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
//         
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void egresos() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            listaegreso = resumenesEgresosDao.getDetallesegreso(session, desde, hasta);
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
    }
}
