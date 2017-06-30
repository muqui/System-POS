/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.logica.Fechas;
import dao.AltaProductoDao;
import dao.DaoTProducto;
import dao.ModificarUsuarioDao;
import dao.ResumenesVentasDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Categoria;
import pojo.Tventadetalle;
import pojo.Usuario;
import vista.JInternalFrameVentasDetalles;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class VentasControlador implements ActionListener {

    Tventadetalle ventaDetalle;
    Principal principal;
    private JInternalFrameVentasDetalles ventasGui;
    Session session;
    Transaction transaction;
    private Date desde;
    private Date hasta;
    private List<Tventadetalle> listaventas;
    private ResumenesVentasDao resumenesVentasDao;
    private ModificarUsuarioDao modificarUsuarioDao;
    List<Usuario> usuarios;
    private List<Categoria> todasCategorias;
    Fechas fechas;

    public VentasControlador(Principal principal, Tventadetalle ventaDetalle, JInternalFrameVentasDetalles ventasGui) {
        fechas = new Fechas();
        this.principal = principal;
        this.ventaDetalle = ventaDetalle;
        this.ventasGui = ventasGui;
        resumenesVentasDao = new ResumenesVentasDao();
        modificarUsuarioDao = new ModificarUsuarioDao();
        principal.jMenuItemVentasDetalle.addActionListener(this);
        ventasGui.jButtonFiltrar.addActionListener(this);
        principal.jButtonVentas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemVentasDetalle || e.getSource() == principal.jButtonVentas) {
            getAllUsuario();
            llenarComboUsuario();
            getTodasCategorias();
            llenarComboCategorias();
            desde = new Date();
            hasta = new Date();
            detalles();
            ventasGui.jTableVentasDetalle.setModel(llenarTabla());
            mostrar();
        }
        if (e.getSource() == ventasGui.jButtonFiltrar) {
            desde = ventasGui.jDateChooserDesde.getDate();
            hasta = ventasGui.jDateChooserHasta.getDate();
            detalles();
            ventasGui.jTableVentasDetalle.setModel(llenarTabla());
        }
    }

    public void mostrar() {
        ventasGui.jDateChooserDesde.setDate(new Date());
        ventasGui.jDateChooserHasta.setDate(new Date());

        if (ventasGui.isVisible()) {
            ventasGui.toFront();

        } else {
            principal.jDesktopPane.add(ventasGui);
            ventasGui.pack();
            ventasGui.setVisible(true);

        }

    }

    public void detalles() {
        String usuario = "" + ventasGui.jComboBoxUsuario.getSelectedItem();
        if (usuario.equals("todos los usuarios")) {
            usuario = "";
        }

        String categoria = "" + ventasGui.jComboBoxConcepto.getSelectedItem();
        if (categoria.equals("todas las categorias")) {
            categoria = "";
        }
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            listaventas = resumenesVentasDao.getDetallesventas(session, desde, hasta, usuario, categoria);
            ventasGui.jLabelTotal.setText("" + resumenesVentasDao.getVentas(session, desde, hasta, usuario, categoria));
            this.transaction.commit();
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
//                this.session.close();
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

        String[] columnNames = {"FECHA", "CODIGO", "NOMBRE", "USUARIO", "CANTIDAD", "PRECIO PROV.", "PRECIO", "TOTAL"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];
        for (int i = 0; i < listaventas.size(); i++) {
            fila[0] = fechas.fecha1(listaventas.get(i).getTventa().getFechaRegistro());
            fila[1] = listaventas.get(i).getTproducto().getCodigoBarras();
            fila[2] = listaventas.get(i).getTproducto().getNombre();
            fila[3] = listaventas.get(i).getTventa().getUsuario().getNombre();
            fila[4] = listaventas.get(i).getCantidad();
            fila[5] = listaventas.get(i).getPrecioProveedor();
            fila[6] = listaventas.get(i).getPrecioVentaUnitarioProducto();
            fila[7] = listaventas.get(i).getTotalPrecioVenta();
            tableModel.addRow(fila);

        }
        return tableModel;
    }

    public void getAllUsuario() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.usuarios = modificarUsuarioDao.getUsuarios(this.session);

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

    public void llenarComboUsuario() {
        ventasGui.jComboBoxUsuario.setModel(new DefaultComboBoxModel());
        //  altaProducto.getjComboBoxCategorias().setModel(new DefaultComboBoxModel());

        ventasGui.jComboBoxUsuario.addItem("todos los usuarios");
        // todasCategorias = getTodasCategorias();
        for (int i = 0; i < usuarios.size(); i++) {
            // System.out.println("" + todasCategorias.get(i).getNombre() + " id " + todasCategorias.get(i).getId());
            ventasGui.jComboBoxUsuario.addItem("" + usuarios.get(i).getNombre());
        }

    }

    public void getTodasCategorias() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            AltaProductoDao altaProductoDao = new AltaProductoDao();

            this.transaction = this.session.beginTransaction();

            //this.listaProducto = daoTProducto.getAll(this.session);
            this.todasCategorias = altaProductoDao.getCategorias(session);
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

    public void llenarComboCategorias() {
        //altaProducto.getjComboBoxCategorias().setModel(new DefaultComboBoxModel());
        ventasGui.jComboBoxConcepto.setModel(new DefaultComboBoxModel());

        ventasGui.jComboBoxConcepto.addItem("todas las categorias");
        for (int i = 0; i < todasCategorias.size(); i++) {

            // System.out.println("" + todasCategorias.get(i).getNombre() + " id " + todasCategorias.get(i).getId());
            ventasGui.jComboBoxConcepto.addItem("" + todasCategorias.get(i).getNombre());
        }

    }
}
