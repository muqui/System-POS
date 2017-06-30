/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTProducto;
import dao.DaoTVenta;
import dao.DaoTVentaDetalle;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Tproducto;
import pojo.Tventa;
import pojo.Tventadetalle;
import pojo.Usuario;
import vista.EditarEliminarVenta;
import vista.JInternalFrameVender;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class VenderControlador implements ActionListener {

    private Principal principal;
    private JInternalFrameVender jInternalFrameVender;
    private EditarEliminarVenta editarEliminarVenta;
    private DefaultTableModel tableModelVentas;
    private BigDecimal precioG; //precio del producto a vender.
    private List<Tventadetalle> listaVentaDetalle;
    private int rowG; //fila actalizar o borrar
    private Tventa venta;
    Session session;
    Transaction transaction;
    private Tproducto producto;
    private List<Tproducto> listaProducto;
    List<Tproducto> productos;

    public VenderControlador(Principal principal, JInternalFrameVender jInternalFrameVender, EditarEliminarVenta editarEliminarVenta) {
        this.principal = principal;
        this.jInternalFrameVender = jInternalFrameVender;
        this.editarEliminarVenta = editarEliminarVenta;
        principal.jButtonVender.addActionListener(this);
        this.editarEliminarVenta.getJbEliminar().addActionListener(this);
        this.editarEliminarVenta.getjBCantidad().addActionListener(this);
        this.jInternalFrameVender.jBVender.addActionListener(this);
          this.setEventoMouseClicked(jInternalFrameVender.jtableProductos); //eventos del mouse
        this.setEventoMouseClicked(jInternalFrameVender.jtableVentas); //eventos del mouse
        this.setEventoText(jInternalFrameVender.txtBuscar);
        tableModelVentas = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO ","PREC. PROVEEDOR", "CANTIDAD", "TOTAL"};
        tableModelVentas.setColumnIdentifiers(columnNames);
        this.venta = new Tventa();
        this.listaVentaDetalle = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jButtonVender) {
            mostrar();
        }
        if (e.getSource() == editarEliminarVenta.getJbEliminar()) {
            eliminarProductoVenta(rowG);
        }
        if (e.getSource() == editarEliminarVenta.getjBCantidad()) {
            modificarProductoVenta(rowG);
        }
        if (e.getSource() == jInternalFrameVender.jBVender) {
            if (tableModelVentas.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No tiene productos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                vender();
            }

        }

    }

    public void mostrar() {
        try {
            if (jInternalFrameVender.isVisible()) {
                jInternalFrameVender.toFront();
                
            } else {
                principal.jDesktopPane.add(jInternalFrameVender);
                jInternalFrameVender.pack();
                jInternalFrameVender.setVisible(true);
                
            }
            jInternalFrameVender.setMaximum(true);
            productos = getAllProducto();
            jInternalFrameVender.jtableProductos.setModel(llenarTabla());
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VenderControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarProductoVenta(int row) {
        tableModelVentas.removeRow(row);

        editarEliminarVenta.dispose();
        jInternalFrameVender.jtableVentas.setModel(tableModelVentas);
        System.out.println("Eliminar producto factura");
        jInternalFrameVender.txtTotalVenta.setText("$ " + calcularCostos1());
    }

    public void modificarProductoVenta(int row) {
        if (editarEliminarVenta.txtCantidad.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Capture una cantidad", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            tableModelVentas.setValueAt(editarEliminarVenta.getTxtCantidad().getText().trim(), row, 4);//actualiza cantidad
            String cantidad = editarEliminarVenta.getTxtCantidad().getText().trim();

            BigDecimal total = precioG.multiply(new BigDecimal(cantidad));

            tableModelVentas.setValueAt(total, row, 5);//actualiza total
            editarEliminarVenta.dispose();
            jInternalFrameVender.jtableVentas.setModel(tableModelVentas);
            editarEliminarVenta.getTxtCantidad().setText("");
            jInternalFrameVender.txtTotalVenta.setText("$ " + calcularCostos1());
        }

    }

    public void tableModelVentas(String codigo, String nombre, BigDecimal precio, BigDecimal precioProv) {
        tableModelVentas.addRow(new Object[]{codigo, nombre, precio, precioProv, "1", precio});

        jInternalFrameVender.jtableVentas.setModel(tableModelVentas);
    }

    public void vender() {

        System.out.println(" id usuario " + principal.usuario.getNivel());
        for (int i = 0; i < tableModelVentas.getRowCount(); i++) {
            String codigo = (String) tableModelVentas.getValueAt(i, 0);
            String nombre = (String) tableModelVentas.getValueAt(i, 1);
            BigDecimal precioUnitario = (BigDecimal) tableModelVentas.getValueAt(i, 2);
             BigDecimal PrecioProveedor = (BigDecimal) tableModelVentas.getValueAt(i, 3);
            String cantidad = (String) tableModelVentas.getValueAt(i, 4);
            BigDecimal total = (BigDecimal) tableModelVentas.getValueAt(i, 5);
           
//listaVentaDetalle.add(new Tventadetalle(null, null, this.producto.getCodigoBarras(), this.producto.getNombre(), this.producto.getPrecioVentaUnitario(), 0, new BigDecimal("0")));
          
              listaVentaDetalle.add(new Tventadetalle(null, null, codigo, nombre, precioUnitario, Integer.parseInt(cantidad), total, PrecioProveedor ));
                    
         

            System.out.println("codigo= " + codigo + " nombre= " + nombre + " Precio U=  " + precioUnitario + " cantidad=  " + cantidad + " total=  " + total +" Precio PROVEEDOR "+ PrecioProveedor);
        }
        venta.setFechaRegistro(new Date());

        calcularCostos1();
        realizarVenta();

    }

    public BigDecimal calcularCostos1() {
        BigDecimal totalVenta = new BigDecimal("0");
        try {

            for (int i = 0; i < tableModelVentas.getRowCount(); i++) {

                // BigDecimal total = (BigDecimal) tableModelVentas.getValueAt(i, 4);
                BigDecimal totalVentaPorProducto = (BigDecimal) tableModelVentas.getValueAt(i, 5);

                totalVenta = totalVenta.add(totalVentaPorProducto);
            }

            this.venta.setPrecioVentaTotal(totalVenta);

        } catch (Exception ex) {

        }
        return totalVenta;
    }

    public void realizarVenta() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();
            DaoTVenta daoTVenta = new DaoTVenta();
            DaoTVentaDetalle daoTVentaDetalle = new DaoTVentaDetalle();

            this.transaction = this.session.beginTransaction();

            this.venta.setUsuario(principal.usuario);

            daoTVenta.insert(this.session, this.venta);
            this.venta = daoTVenta.getUltimoRegistro(this.session);

            for (Tventadetalle item : this.listaVentaDetalle) {
                this.producto = daoTProducto.getByCodigoBarras(this.session, item.getCodigoBarrasProducto());
                item.setTventa(this.venta);
                item.setTproducto(this.producto);

                daoTVentaDetalle.insert(this.session, item);
            }
            this.listaProducto = daoTProducto.getAll(session);
            this.transaction.commit();

            this.listaVentaDetalle = new ArrayList<>();
            this.venta = new Tventa();
            JOptionPane.showMessageDialog(null, "venta realizada correctamente ", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "" + ex, "Error", JOptionPane.ERROR_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        System.out.println("REALIZAR VENTA");
        productos = getAllProducto();
        jInternalFrameVender.jtableProductos.setModel(llenarTabla());
        if (tableModelVentas.getRowCount() > 0) {
            for (int i = tableModelVentas.getRowCount() - 1; i > -1; i--) {
                tableModelVentas.removeRow(i);
            }

        }
        jInternalFrameVender.txtTotalVenta.setText(" $ 0.00");
        jInternalFrameVender.jtableVentas.setModel(tableModelVentas);
        jInternalFrameVender.txtBuscar.setText("");

    }

    public List<Tproducto> getAllProducto() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.listaProducto = daoTProducto.getAll(this.session);

            this.transaction.commit();

            return this.listaProducto;
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public List<Tproducto> getAllProductoPorNombre() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.listaProducto = daoTProducto.getPorNombre(session, jInternalFrameVender.txtBuscar.getText());

            this.transaction.commit();

            return this.listaProducto;
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public DefaultTableModel llenarTabla() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"CODIGO", "NOMBRE", "PRECIO", "CANTIDAD", "DESCRIPCION", "PREC.PROVEEDOR"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < productos.size(); i++) {

//fila[0] = productos.get(i).getId();
            fila[0] = productos.get(i).getCodigoBarras();
            fila[1] = productos.get(i).getNombre();
            fila[2] = productos.get(i).getPrecioVentaUnitario();
            fila[3] = productos.get(i).getCantidad();
            fila[4] = productos.get(i).getDescripcion();
            fila[5] = productos.get(i).getPrecioProveedor();
//fila[6] = productos.get(i).getPrecioProveedor();
//fila[7] = productos.get(i).getIdCategoria();
            if (productos.get(i).getCategoria().getId() != 1) //filtra los productos marinela
            {
                tableModel.addRow(fila);
            }
        }
        return tableModel;
    }
      private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {

        String cadena = "";

        int row = jInternalFrameVender.jtableVentas.rowAtPoint(evt.getPoint());
//        if (row >= 0 && principal.getJtableVentas().isEnabled()) {
//            for (int i=0; i < principal.getJtableVentas().getColumnCount();i++)
//            {
//               cadena=cadena + " " +  principal.getJtableVentas().getValueAt(row,i).toString();
//            }
//
//        }
        rowG = row;
        precioG = (BigDecimal) jInternalFrameVender.jtableVentas.getValueAt(row, 2);

        System.out.println("fila seleccionada " + row + "precio " + precioG);
        editarEliminarVenta.setVisible(true);

    }
      private void setEventoText(JTextField textfiel) {
        textfiel.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                productosPorNombre(jInternalFrameVender.txtBuscar.getText());
            }
        });
    }

    private void setEventoMouseClicked(JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == jInternalFrameVender.jtableProductos) {
                    tablaProductosMouseClicked(e);
                }
                if (e.getSource() == jInternalFrameVender.jtableVentas) {
                    if (e.getClickCount() == 2) {
                        tablaVentasMouseClicked(e);
                    }
                    //tablaVentasMouseClicked(e);
                }
            }
        });
    }
      public void productosPorNombre(String nombre) {
        productos = getAllProductoPorNombre();
        jInternalFrameVender.jtableProductos.setModel(llenarTabla());
    }
 private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {

        String cadena = "";

        int row = jInternalFrameVender.jtableProductos.rowAtPoint(evt.getPoint());
        if (row >= 0 && jInternalFrameVender.jtableProductos.isEnabled()) {
//            for (int i=0; i < principal.getJtableProductos().getColumnCount();i++)
//            {
//               cadena=cadena + " " +  principal.getJtableProductos().getValueAt(row,i).toString();
//            }

        }
        tableModelVentas(jInternalFrameVender.jtableProductos.getValueAt(row, 0).toString(), jInternalFrameVender.jtableProductos.getValueAt(row, 1).toString(), (BigDecimal) jInternalFrameVender.jtableProductos.getValueAt(row, 2), (BigDecimal) jInternalFrameVender.jtableProductos.getValueAt(row, 5));
        jInternalFrameVender.txtTotalVenta.setText("$ " + calcularCostos1());
// JOptionPane.showMessageDialog(null, cadena);
    }
}
