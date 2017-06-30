/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author mq12
 */
public class JInternalFrameReporteTotal extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewJInternalFrame
     */
    public JInternalFrameReporteTotal() {
        
            initComponents();
           
             ImageIcon titleIcon0 = new ImageIcon( getClass().getResource("/images/database.png" ) );
            setFrameIcon(titleIcon0);
        
            
            jTableReporte.getTableHeader().setReorderingAllowed(false);
            jTableReporte.getColumn("Cantidad").setCellRenderer(new DerechaTableCellRenderer());
            jTableReporte.getColumn("Total").setCellRenderer(new DerechaTableCellRenderer());
            jTableReporte.getColumn("Cant.").setCellRenderer(new DerechaTableCellRenderer());
         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelFecha = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooserDesde = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateChooserHasta = new com.toedter.calendar.JDateChooser();
        jButtonMostrar = new javax.swing.JButton();
        jButtonAnterior = new javax.swing.JButton();
        jButtonHoy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableReporte = new javax.swing.JTable();

        setClosable(true);
        setTitle("Reporte Total");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabelFecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFecha.setText("Fecha");

        jLabel3.setText("desde:");

        jLabel4.setText("hasta:");

        jButtonMostrar.setText("Mostrar");

        jButtonAnterior.setText("Dia anterior");

        jButtonHoy.setText("Hoy");

        jTableReporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTableReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Renta", null, "Nomina", null, null},
                {"", null, "Internet", null, null},
                {null, null, "Perdidas", null, null},
                {null, null, "Electricidad", null, null},
                {null, null, "Compras", null, null},
                {null, null, "Gastos", null, null},
                {"Pan", null, "Pan", null, null},
                {"Tiempo Aire", null, "Tiempo Aire", null, null},
                {"Copias", null, "Copias", null, null},
                {"Impresion B&N", null, "Impresion  B&N", null, null},
                {"Papeleria", null, "Papeleria", null, null},
                {"Impresion Color", null, "Impresion Color", null, null},
                {"Servicio", null, null, null, null},
                {"Servicio Tecnico", null, null, null, null},
                {"Total", null, null, null, null}
            },
            new String [] {
                "Ingreso", "Cantidad", "Egreso", "Cant.", "Total"
            }
        ));
        jTableReporte.setEnabled(false);
        jTableReporte.setRowHeight(22);
        jScrollPane1.setViewportView(jTableReporte);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(402, 402, 402)
                                .addComponent(jButtonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jDateChooserDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jDateChooserHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonMostrar))))
                        .addGap(0, 245, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonMostrar)
                    .addComponent(jDateChooserHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jDateChooserDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonHoy)
                    .addComponent(jButtonAnterior))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonAnterior;
    public javax.swing.JButton jButtonHoy;
    public javax.swing.JButton jButtonMostrar;
    public com.toedter.calendar.JDateChooser jDateChooserDesde;
    public com.toedter.calendar.JDateChooser jDateChooserHasta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabelFecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableReporte;
    // End of variables declaration//GEN-END:variables
}
