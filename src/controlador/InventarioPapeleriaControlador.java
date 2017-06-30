/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTProducto;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Tproducto;
import vista.DerechaTableCellRenderer;
import vista.JInternalFrameInventarioPapeleria;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class InventarioPapeleriaControlador implements ActionListener {

    Session session;
    Transaction transaction;
    Principal principal;
    JInternalFrameInventarioPapeleria inventario;
    List<Tproducto> papeleria;

    public InventarioPapeleriaControlador(Principal principal, JInternalFrameInventarioPapeleria inventario) {
        this.principal = principal;
        this.inventario = inventario;
        this.principal.jMenuItemInventarioPapeleria.addActionListener(this);
         this.principal.jButtonInventario.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemInventarioPapeleria || e.getSource() == principal.jButtonInventario) {
            inventario.jTableInventario.setModel(llenarTabla());
             inventario.jTableInventario.getColumn("TOTAL VENTA").setCellRenderer(new DerechaTableCellRenderer());
             inventario.jTableInventario.getColumn("TOTAL COMPRA").setCellRenderer(new DerechaTableCellRenderer());
             
           mostrar();
        }
    }
public void mostrar() {
        if (inventario.isVisible()) {
            inventario.toFront();
           

        } else {
            principal.jDesktopPane.add(inventario);
            inventario.pack();
            inventario.setVisible(true);
          
        }
        
    }

    public void inventario() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.papeleria = daoTProducto.getPapeleria(this.session);
//            System.out.println("total " + daoTProducto.totalPapeleria(session));
            inventario.jLabelTotal.setText("$ " +daoTProducto.totalPapeleria(session));
            inventario.jLabelTotalProveedor.setText("$ " +daoTProducto.totalPapeleriaProveedor(session));
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

    public DefaultTableModel llenarTabla() {
        inventario();
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO VENTA", "PRECIO COMPRA", "CANTIDAD", "TOTAL VENTA", "TOTAL COMPRA"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < papeleria.size(); i++) {

            fila[0] = papeleria.get(i).getCodigoBarras();
            fila[1] = papeleria.get(i).getNombre();
            fila[2] = papeleria.get(i).getPrecioVentaUnitario();
            fila[3] = papeleria.get(i).getPrecioProveedor();
            fila[4] = papeleria.get(i).getCantidad();
            BigDecimal totalVenta;
            BigDecimal totalCompra;
            BigDecimal cantidad = new BigDecimal(papeleria.get(i).getCantidad());
            totalCompra = papeleria.get(i).getPrecioProveedor().multiply(cantidad);
            totalVenta = papeleria.get(i).getPrecioVentaUnitario().multiply(cantidad);
            fila[5] = totalVenta;
            fila[6] = totalCompra;
            tableModel.addRow(fila);

        }
        return tableModel;
    }
}
