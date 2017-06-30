/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.logica.Fechas;
import dao.ResumenesDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vista.JInternalFrameReporteTotal;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class TotalVentasControlador implements ActionListener {
    
    private Fechas fechas;
    private int ayer = -1;
    private Principal principal;
    private JInternalFrameReporteTotal totalGui;
    Session session;
    Transaction transaction;
    private Date desde;
    private Date hasta;
    private BigDecimal ventas = new java.math.BigDecimal("0.00");
//Totales
    private BigDecimal ingresos = new java.math.BigDecimal("0.00");
    private BigDecimal egresos = new java.math.BigDecimal("0.00");
    private BigDecimal total;
    private BigDecimal ventasPan = new java.math.BigDecimal("0.00");
    private BigDecimal ventasPanEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal ventasByN = new java.math.BigDecimal("0.00");
    private BigDecimal ventasByNEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal ventasColor = new java.math.BigDecimal("0.00");
    private BigDecimal ventasColorEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal ventasCopia = new java.math.BigDecimal("0.00");
    private BigDecimal ventasPapeleria = new java.math.BigDecimal("0.00");
    private BigDecimal ventasPapeleriaEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal nominaEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal tiempoAireIngreso = new java.math.BigDecimal("0.00");
    private BigDecimal tiempoAireEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal rentaIngres = new java.math.BigDecimal("0.00");
    private BigDecimal internetEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal perdidasEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal luzEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal comprasEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal gastosEgreso = new java.math.BigDecimal("0.00");
    private BigDecimal servicio = new java.math.BigDecimal("0.00");
     private BigDecimal servicioTecnico = new java.math.BigDecimal("0.00");
    private ResumenesDao resumenesDao;
    
    public TotalVentasControlador(Principal principal, JInternalFrameReporteTotal totalGui) {
        this.principal = principal;
        this.totalGui = totalGui;
        resumenesDao = new ResumenesDao();
        fechas = new Fechas();
        principal.jMenuItemTotal.addActionListener(this);
        totalGui.jButtonMostrar.addActionListener(this);
        principal.jButtonTotal.addActionListener(this);
        totalGui.jButtonAnterior.addActionListener(this);
        totalGui.jButtonHoy.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemTotal || e.getSource() == principal.jButtonTotal) {
            diaActual();
        }
        if (e.getSource() == totalGui.jButtonMostrar) {
            desde = totalGui.jDateChooserDesde.getDate();
            hasta = totalGui.jDateChooserHasta.getDate();
            totalGui.jLabelFecha.setText(fechas.rangoFecha(desde, hasta));
            actualizarventas();
            
        }
        if (e.getSource() == totalGui.jButtonAnterior) {
            desde = fechas.ayer();
            hasta = fechas.ayer();
            actualizarventas();
            totalGui.setVisible(true);
            totalGui.jLabelFecha.setText("" + fechas.fecha(desde));
            fechas.ayer--;
            
        }
        if (e.getSource() == totalGui.jButtonHoy) {
            diaActual();
        }
    }
    
    public void diaActual() {
        desde = new Date();
        hasta = new Date();
        actualizarventas();
        totalGui.jLabelFecha.setText("" + fechas.fecha(desde));
        mostrar();
        fechas.ayer = -1;
    }
    
    public void mostrar() {
        totalGui.jDateChooserDesde.setDate(new Date());
        totalGui.jDateChooserHasta.setDate(new Date());
        if (totalGui.isVisible()) {
            totalGui.toFront();
            
        } else {
            principal.jDesktopPane.add(totalGui);
            totalGui.pack();
            totalGui.setVisible(true);
            
        }
        
    }
    
    public void actualizarventas() {
        total = new java.math.BigDecimal("0.00");
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            //totales
            ventas = getResumenesDao().getVentas(session, desde, hasta);
            ingresos = getResumenesDao().getIngresos(session, desde, hasta);
            egresos = getResumenesDao().getEgresos(session, desde, hasta);
            
             servicioTecnico = getResumenesDao().getVentas(session, desde, hasta, "Rep. Serv. Tecnico");
           totalGui.jTableReporte.setValueAt(servicioTecnico, 13, 1); //servicio Tecnicoingreso
           totalGui.jTableReporte.setValueAt(servicioTecnico, 13, 4); //servicio Tecnico ingreso
            
            
            servicio = getResumenesDao().getVentas(session, desde, hasta, "Servicio");
           totalGui.jTableReporte.setValueAt(servicio, 12, 1); //servicio ingreso
           totalGui.jTableReporte.setValueAt(servicio, 12, 4); //servicio ingreso
            
            ventasPan = getResumenesDao().getVentas(session, desde, hasta, "Pan");
           totalGui.jTableReporte.setValueAt(ventasPan, 6, 1); //Pan ingreso
            ventasPanEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Pan");

            totalGui.jTableReporte.setValueAt(ventasPanEgreso, 6, 3); //Pan egreso
            totalGui.jTableReporte.setValueAt((ventasPan.subtract(ventasPanEgreso)), 6, 4); //Total Pan
            
            ventasByN = getResumenesDao().getVentas(session, desde, hasta, "Impresion B&N");
            totalGui.jTableReporte.setValueAt(ventasByN, 9, 1); //ByN ingreso
            ventasByNEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Impresion ByN");
            totalGui.jTableReporte.setValueAt(ventasByNEgreso, 9, 3); //Byn egreso
            totalGui.jTableReporte.setValueAt((ventasByN.subtract(ventasByNEgreso)), 9, 4); //ByN Total
            
            ventasColor = getResumenesDao().getVentas(session, desde, hasta, "Impresion Color");
            totalGui.jTableReporte.setValueAt(ventasColor, 11, 1); //color ingreso
            ventasColorEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Impresion Color");
            totalGui.jTableReporte.setValueAt(ventasColorEgreso, 11, 3); // Color egreso
            totalGui.jTableReporte.setValueAt(ventasColor.subtract(ventasColorEgreso), 11, 4); //color ingreso

                      
            ventasCopia = getResumenesDao().getVentas(session, desde, hasta, "Copias");
            totalGui.jTableReporte.setValueAt(ventasCopia, 8, 1); //copias ingreso
            totalGui.jTableReporte.setValueAt("0.00", 8, 3); //copia egreso
            totalGui.jTableReporte.setValueAt(ventasCopia, 8, 4); //copias ingreso


            ventasPapeleria = getResumenesDao().getVentas(session, desde, hasta, "Papeleria");
            totalGui.jTableReporte.setValueAt(ventasPapeleria, 10, 1);
            ventasPapeleriaEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Papeleria");
            totalGui.jTableReporte.setValueAt(ventasPapeleriaEgreso, 10, 3);
            totalGui.jTableReporte.setValueAt(ventasPapeleria.subtract(ventasPapeleriaEgreso), 10, 4);

         
            
            tiempoAireEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Tiempo aire");
            totalGui.jTableReporte.setValueAt(tiempoAireEgreso, 7, 3); //Tiempo aire
            tiempoAireIngreso = getResumenesDao().getIngresos(session, desde, hasta, "Tiempo aire");
            totalGui.jTableReporte.setValueAt(tiempoAireIngreso, 7, 1); // Tiempo aire ingreso.
            totalGui.jTableReporte.setValueAt(tiempoAireIngreso.subtract(tiempoAireEgreso), 7, 4); //Tiempo aire
            

            rentaIngres = getResumenesDao().getIngresos(session, desde, hasta, "Renta PC");
            totalGui.jTableReporte.setValueAt(rentaIngres, 0, 1); //renta pc ingreso

              nominaEgreso = getResumenesDao().getEgresos(session, desde, hasta, "nomina");
            totalGui.jTableReporte.setValueAt(nominaEgreso, 0, 3);
            rentaIngres = rentaIngres.subtract(nominaEgreso);
            
            perdidasEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Perdidas");
            totalGui.jTableReporte.setValueAt(perdidasEgreso, 2, 3); //perdidas egreso
             rentaIngres=  rentaIngres.subtract(perdidasEgreso);
//            totalGui.jTableVentas.setValueAt("0.00", 8, 1);
//            totalGui.jTableVentas.setValueAt(perdidasEgreso, 8, 2);

            internetEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Internet");
            totalGui.jTableReporte.setValueAt(internetEgreso, 1, 3); //internet egreso. 
              rentaIngres= rentaIngres.subtract(internetEgreso);


            luzEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Luz");
            totalGui.jTableReporte.setValueAt(luzEgreso, 3, 3); //Electricidad
              rentaIngres= rentaIngres.subtract(luzEgreso);


            comprasEgreso = getResumenesDao().getEgresos(session, desde, hasta, "Compras");
            totalGui.jTableReporte.setValueAt(comprasEgreso, 4, 3); //compras
              rentaIngres= rentaIngres.subtract(comprasEgreso);


            gastosEgreso = getResumenesDao().getEgresos(session, desde, hasta, "gastos");
            totalGui.jTableReporte.setValueAt(gastosEgreso, 5, 3);
              rentaIngres= rentaIngres.subtract(gastosEgreso);             
               totalGui.jTableReporte.setValueAt(rentaIngres, 0, 4);

            //Suma total
            total = total.add(ventas);
            total = total.add(ingresos);
            
            totalGui.jTableReporte.setValueAt(total, 14, 1);
            totalGui.jTableReporte.setValueAt(egresos, 14, 3);
            total = total.subtract(egresos);
            
            totalGui.jTableReporte.setValueAt(total, 14, 4);
           
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

    /**
     * @param resumenesDao the resumenesDao to set
     */
    public void setResumenesDao(ResumenesDao resumenesDao) {
        this.resumenesDao = resumenesDao;
    }

    /**
     * @return the resumenesDao
     */
    public ResumenesDao getResumenesDao() {
        return resumenesDao;
    }
}
