/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.AltaProductoDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Categoria;
import pojo.Tproducto;
import pojo.Usuario;
import vista.JInternalFrameAltaProducto;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class ControladorAltaProducto implements ActionListener {

    private Principal principal;
    private Usuario usuario;
    
    private JInternalFrameAltaProducto altaProducto;
    Session session;
    Transaction transaction;
    private Tproducto producto;
    //private AltaProductoDao altaProductoDao;
    private List<Categoria> todasCategorias;
    private Categoria categoria;

    public ControladorAltaProducto(Principal principal, Usuario usuario, JInternalFrameAltaProducto altaProducto) {
        producto = new Tproducto();
        this.principal = principal;
        this.usuario = usuario;
        this.altaProducto = altaProducto;
        this.principal.jMenuItemAltaProducto.addActionListener(this);
        this.altaProducto.jButtonGuardar.addActionListener(this);
        this.principal.jButtonAltaProducto.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemAltaProducto || e.getSource() == principal.jButtonAltaProducto) {
            mostrar();
            llenarCombo();
        }
        if (e.getSource() == altaProducto.jButtonGuardar) {
            if (validar() == true) {
                altaProducto();
            }

        }

    }
 public void mostrar() {
        if (altaProducto.isVisible()) {
            altaProducto.toFront();
            System.out.println("valor al frente" + altaProducto.isVisible());

        } else {
            principal.jDesktopPane.add(altaProducto);
            altaProducto.pack();
            altaProducto.setVisible(true);
            System.out.println("valor " + altaProducto.isVisible());
        }
        
    }
    public void altaProducto() {
        String nombre = "" + altaProducto.jComboBoxCategorias.getSelectedItem();
        producto.setCodigoBarras(altaProducto.txtCodigo.getText().trim());
        producto.setNombre(altaProducto.txtNombre.getText().trim());
        producto.setDescripcion(altaProducto.txtDescripcion.getText().trim());
        producto.setPrecioProveedor(new BigDecimal(altaProducto.txtPrecioProv.getText().trim()));
        producto.setPrecioVentaUnitario(new BigDecimal(altaProducto.txtPrecioPub.getText().trim()));
        producto.setCantidad(Integer.parseInt(altaProducto.txtExistencia.getText().trim()));

        getProducto().setCategoria(getCategoriaPorNombre(nombre));
        System.out.println("id " + categoria.getId() + " nombre " + categoria.getNombre());
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            System.out.println("proveedor " + getProducto().getPrecioProveedor());
            AltaProductoDao altaProductoDao = new AltaProductoDao();
            altaProductoDao.altaProducto(getProducto(), session);
            altaProducto.setVisible(false);
            JOptionPane.showMessageDialog(null, "Producto dado de alta. ", "Exito", JOptionPane.INFORMATION_MESSAGE);
            altaProducto.txtCodigo.setText("");
            altaProducto.txtDescripcion.setText("");
            altaProducto.txtExistencia.setText("");
            altaProducto.txtNombre.setText("");
            altaProducto.txtPrecioProv.setText("");
            altaProducto.txtPrecioPub.setText("");
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

    }

    public List<Categoria> getTodasCategorias() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            AltaProductoDao altaProductoDao = new AltaProductoDao();

            this.transaction = this.session.beginTransaction();

            //this.listaProducto = daoTProducto.getAll(this.session);
            this.todasCategorias = altaProductoDao.getCategorias(session);
            this.transaction.commit();

            return this.todasCategorias;
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

    public Categoria getCategoriaPorNombre(String nombre) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            AltaProductoDao altaProductoDao = new AltaProductoDao();

            this.transaction = this.session.beginTransaction();
            this.categoria = altaProductoDao.getCategoria(session, nombre);
            this.transaction.commit();
            return this.categoria;
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            return null;
        } finally {
            if (this.session != null) {
                //this.session.close();
            }
        }
    }

    public void llenarCombo() {
        altaProducto.jComboBoxCategorias.setModel(new DefaultComboBoxModel());

        System.out.println("llenar combo");
        todasCategorias = getTodasCategorias();
        for (int i = 0; i < todasCategorias.size(); i++) {
            System.out.println("" + todasCategorias.get(i).getNombre() + " id " + todasCategorias.get(i).getId());
            altaProducto.jComboBoxCategorias.addItem("" + todasCategorias.get(i).getNombre());
        }

        altaProducto.setVisible(true);
    }

    /**
     * @return the producto
     */
    public Tproducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Tproducto producto) {
        this.producto = producto;
    }

    public boolean validar() {
        boolean bandera = false;
        if (!"".equals(altaProducto.txtCodigo.getText().trim()) && !"".equals(altaProducto.txtDescripcion.getText().trim())
                && !"".equals(altaProducto.txtExistencia.getText().trim())
                && !"".equals(altaProducto.txtNombre.getText().trim())
                && !"".equals(altaProducto.txtPrecioProv.getText().trim())
                && !"".equals(altaProducto.txtPrecioPub.getText().trim())) {
            bandera = true;
        } else {
            JOptionPane.showMessageDialog(null, "Capture todos los campos ", "error", JOptionPane.ERROR_MESSAGE);

        }
        return bandera;
    }
}
