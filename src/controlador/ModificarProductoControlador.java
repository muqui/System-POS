/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTProducto;
import dao.ModificarProductoDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Categoria;
import pojo.Tproducto;
import validador.ValidarEntradas;
import vista.JInternalFrameModificarProducto;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class ModificarProductoControlador implements ActionListener {

    Session session;
    Transaction transaction;
    private Principal principal;
    private Tproducto producto;
    private JInternalFrameModificarProducto modificarProducto;
    List<Tproducto> productos;
    private List<Tproducto> listaProducto;
    Categoria categoria;

    public ModificarProductoControlador(Principal principal, Tproducto producto, JInternalFrameModificarProducto modificarProducto) {
        this.principal = principal;
        this.producto = producto;
        this.modificarProducto = modificarProducto;
        this.principal.jMenuItemModificarProducto.addActionListener(this);
        this.modificarProducto.jButtonGuardar.addActionListener(this);
        this.principal.jButtonModificarProducto.addActionListener(this);

        this.setEventoMouseClicked(modificarProducto.jTableProductos); //eventos del mouse
        this.setEventoText(modificarProducto.txtBuscar);
        categoria = new Categoria();

    }

    public void iniciar() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemModificarProducto || e.getSource() == principal.jButtonModificarProducto) {
             mostrar();
            productos = getAllProducto();
            modificarProducto.jTableProductos.setModel(llenarTabla());

          
        }
        if (e.getSource() == modificarProducto.jButtonGuardar) {
            modificar();
        }
    }
    
 public void mostrar() {
        if (modificarProducto.isVisible()) {
            modificarProducto.toFront();
            System.out.println("valor al frente" + modificarProducto.isVisible());

        } else {
            principal.jDesktopPane.add(modificarProducto);
            modificarProducto.pack();
            modificarProducto.setVisible(true);
            System.out.println("valor " + modificarProducto.isVisible());
        }
        
    }
    private void setEventoMouseClicked(JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                if (e.getSource() == principal.jtableProductos) {
//                    tablaProductosMouseClicked(e);
//                }
                if (e.getSource() == modificarProducto.jTableProductos) {
                    if (e.getClickCount() == 2) {
                        tablaVentasMouseClicked(e);
                    }
                    //tablaVentasMouseClicked(e);
                }
            }

        });
    }

    private void setEventoText(JTextField textfiel) {
        textfiel.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                productosPorNombre(modificarProducto.txtBuscar.getText());
            }
        });
    }

    private void tablaVentasMouseClicked(MouseEvent evt) {
        String cadena = "";
        int row = modificarProducto.jTableProductos.rowAtPoint(evt.getPoint());
        if (row >= 0 && modificarProducto.jTableProductos.isEnabled()) {
            this.producto = seleccion("" + modificarProducto.jTableProductos.getValueAt(row, 1));
            modificarProducto.txtID.setText("" + modificarProducto.jTableProductos.getValueAt(row, 0));
            modificarProducto.txtCodigo.setText("" + modificarProducto.jTableProductos.getValueAt(row, 1));
            modificarProducto.txtNombre.setText("" + modificarProducto.jTableProductos.getValueAt(row, 2));
            modificarProducto.txtPrecioPub.setText("" + modificarProducto.jTableProductos.getValueAt(row, 3));
            modificarProducto.txtExistencia.setText("" + modificarProducto.jTableProductos.getValueAt(row, 4));
            modificarProducto.txtDescripcion.setText("" + modificarProducto.jTableProductos.getValueAt(row, 5));
            modificarProducto.txtPrecioProv.setText("" + modificarProducto.jTableProductos.getValueAt(row, 6));
        }
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

    public DefaultTableModel llenarTabla() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID", "CODIGO", "NOMBRE", "PRECIO", "CANTIDAD", "DESCRIPCION", "PRECIO PROV."};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < productos.size(); i++) {

//fila[0] = productos.get(i).getId();
            fila[0] = productos.get(i).getIdProducto();
            fila[1] = productos.get(i).getCodigoBarras();
            fila[2] = productos.get(i).getNombre();
            fila[3] = productos.get(i).getPrecioVentaUnitario();
            fila[4] = productos.get(i).getCantidad();
            fila[5] = productos.get(i).getDescripcion();
            fila[6] = productos.get(i).getPrecioProveedor();
//fila[6] = productos.get(i).getPrecioProveedor();
//fila[7] = productos.get(i).getIdCategoria();
            if (productos.get(i).getCategoria().getId() != 1) //filtra los productos marinela
            {
                tableModel.addRow(fila);
            }
        }
        return tableModel;
    }

    public void productosPorNombre(String nombre) {
        System.out.println("nombre " + nombre);
        productos = getAllProductoPorNombre();
        modificarProducto.jTableProductos.setModel(llenarTabla());
    }

    public List<Tproducto> getAllProductoPorNombre() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.listaProducto = daoTProducto.getPorNombre(session, modificarProducto.txtBuscar.getText());

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

    public void modificar() {

        System.out.println("categora " + producto.getCategoria().getId());
        categoria.setId(producto.getCategoria().getId());
        System.out.println("evento modificar ");
        producto.setCategoria(categoria);
        producto.setIdProducto(Integer.parseInt(modificarProducto.txtID.getText().trim()));
        producto.setCodigoBarras(modificarProducto.txtCodigo.getText().trim());
        producto.setNombre(modificarProducto.txtNombre.getText().trim());
        producto.setDescripcion(modificarProducto.txtDescripcion.getText().trim());
        producto.setPrecioProveedor(new BigDecimal(modificarProducto.txtPrecioProv.getText().trim()));
        producto.setPrecioVentaUnitario(new BigDecimal(modificarProducto.txtPrecioPub.getText().trim()));
        producto.setCantidad(Integer.parseInt(modificarProducto.txtExistencia.getText().trim()));

        ModificarProductoDao modificarProductoDao = new ModificarProductoDao();
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            this.transaction = this.session.beginTransaction();
            modificarProductoDao.modificarProducto(producto, session);
            limpiar();
            JOptionPane.showMessageDialog(null, "Producto modificado. ", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.transaction.commit();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "error", JOptionPane.ERROR_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        productos = getAllProducto();
        modificarProducto.jTableProductos.setModel(llenarTabla());
    }

    public Tproducto seleccion(String id) {
        Tproducto p = null;

        DaoTProducto daoTProducto = new DaoTProducto();
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            this.transaction = this.session.beginTransaction();
            p = daoTProducto.getByCodigoBarras(this.session, id);
            this.transaction.commit();

        } catch (Exception ex) {
            System.out.println("error producto " + ex);
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        return p;
    }

    public void limpiar() {
        modificarProducto.txtBuscar.setText("");
        modificarProducto.txtCodigo.setText("");
        modificarProducto.txtDescripcion.setText("");
        modificarProducto.txtExistencia.setText("");
        modificarProducto.txtID.setText("");
        modificarProducto.txtNombre.setText("");
        modificarProducto.txtPrecioProv.setText("");
        modificarProducto.txtPrecioPub.setText("");

    }
}
