/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.ImageIcon;

/**
 *
 * @author mq12
 */
public class JInternalFrameInventarioPapeleria extends javax.swing.JInternalFrame {

    /**
     * Creates new form JInternalFrameInventarioPapeleria
     */
    public JInternalFrameInventarioPapeleria() {
        initComponents();
         ImageIcon titleIcon0 = new ImageIcon( getClass().getResource("/images/database.png" ) );
            setFrameIcon(titleIcon0);
        jTableInventario.getTableHeader().setReorderingAllowed(false);
         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInventario = new javax.swing.JTable();
        jLabelTotal = new javax.swing.JLabel();
        jLabelTotalProveedor = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setTitle("Inventario Papeleria");

        jTableInventario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTableInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableInventario.setRowHeight(20);
        jTableInventario.setRowMargin(2);
        jScrollPane1.setViewportView(jTableInventario);

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setText("jLabel2");

        jLabelTotalProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalProveedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalProveedor.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotalProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotalProveedor)
                    .addComponent(jLabelTotal))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabelTotal;
    public javax.swing.JLabel jLabelTotalProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableInventario;
    // End of variables declaration//GEN-END:variables
}
