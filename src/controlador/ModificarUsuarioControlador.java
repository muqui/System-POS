/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTProducto;
import dao.ModificarUsuarioDao;
import encryption.Encryption;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Tproducto;
import pojo.Usuario;
import vista.JInternalFrameModificarUsuario;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class ModificarUsuarioControlador implements ActionListener {

    Session session;
    Transaction transaction;
    private Principal principal;
    private JInternalFrameModificarUsuario modificarUsuarioGui;
    private Usuario usuario;
    private ModificarUsuarioDao modificarUsuarioDao;
    List<Usuario> usuarios;
    private int id;

    public ModificarUsuarioControlador(Principal principal, Usuario usuario, JInternalFrameModificarUsuario modificarUsuarioGui) {
        this.principal = principal;
        this.usuario = usuario;
        this.modificarUsuarioGui = modificarUsuarioGui;
        modificarUsuarioDao = new ModificarUsuarioDao();
        principal.jMenuItemModificarUsuario.addActionListener(this);
        modificarUsuarioGui.jButtonModificar.addActionListener(this);
        this.setEventoMouseClicked(modificarUsuarioGui.jTableUsuario); //eventos del mouse
        this.principal.jButtonModificarUsuario.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemModificarUsuario || e.getSource() == principal.jButtonModificarUsuario) {
//             modificarProducto.getjTableProductos().setModel(llenarTabla());
            modificarUsuarioGui.jTableUsuario.setModel(llenarTabla());
            mostrar();
        }
        if (e.getSource() == modificarUsuarioGui.jButtonModificar) {
            if (modificarUsuarioGui.txtContraseña.getText().equals("") || modificarUsuarioGui.txtConfirmar.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                modificar();
                modificarUsuarioGui.jTableUsuario.setModel(llenarTabla());
            }

        }
    }
 public void mostrar() {
        if (modificarUsuarioGui.isVisible()) {
            modificarUsuarioGui.toFront();
           

        } else {
            principal.jDesktopPane.add(modificarUsuarioGui);
            modificarUsuarioGui.pack();
            modificarUsuarioGui.setVisible(true);
          
        }
        
    }
    private void setEventoMouseClicked(JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
//              
                if (e.getSource() == modificarUsuarioGui.jTableUsuario) {
                    if (e.getClickCount() == 2) {
                        tablaUsuarioMouseClicked(e);
                    }

                }
            }

            private void tablaUsuarioMouseClicked(MouseEvent evt) {
                String cadena = "";
                int row = modificarUsuarioGui.jTableUsuario.rowAtPoint(evt.getPoint());
                if (row >= 0 && modificarUsuarioGui.jTableUsuario.isEnabled()) {

                    id = (int) modificarUsuarioGui.jTableUsuario.getValueAt(row, 0);
                    modificarUsuarioGui.txtNombre.setText(""+ modificarUsuarioGui.jTableUsuario.getValueAt(row, 2));
                }

            }

        });
    }

    public List<Usuario> getAllUsuario() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            DaoTProducto daoTProducto = new DaoTProducto();

            this.transaction = this.session.beginTransaction();

            this.usuarios = modificarUsuarioDao.getUsuarios(this.session);

            this.transaction.commit();

            return this.usuarios;
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
        List<Usuario> u = getAllUsuario();
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        String[] columnNames = {"ID", "NIVEL", "NOMBRE"};
        tableModel.setColumnIdentifiers(columnNames);
        Object[] fila = new Object[tableModel.getColumnCount()];

        for (int i = 0; i < u.size(); i++) {
            fila[0] = u.get(i).getId();
            fila[1] = u.get(i).getNivel();
            fila[2] = u.get(i).getNombre();

            tableModel.addRow(fila);

        }
        return tableModel;
    }

    public void modificar() {
        
        String password = modificarUsuarioGui.txtContraseña.getText();
        String confirmar = modificarUsuarioGui.txtConfirmar.getText();

        usuario.setNivel(Integer.parseInt("" + modificarUsuarioGui.jComboBoxTipoMovimiento.getSelectedItem()));
        usuario.setNombre(modificarUsuarioGui.txtNombre.getText().trim());
        usuario.setPassword(modificarUsuarioGui.txtContraseña.getText());
        
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
           
            usuario.setId(id);
            if (password.equals(confirmar)) {

                usuario.setPassword(Encryption.encrypt(usuario.getPassword()));

                modificarUsuarioDao.actualizar(usuario, session);
                modificarUsuarioGui.txtConfirmar.setText("");
                modificarUsuarioGui.txtContraseña.setText("");
                modificarUsuarioGui.txtNombre.setText("");
                JOptionPane.showMessageDialog(null, "Movimiento registrado", "Exito", JOptionPane.INFORMATION_MESSAGE);
                this.transaction.commit();
                // this.session.close();

            } else {
                JOptionPane.showMessageDialog(null, "Contraseña no coincide", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("error actiualizar" + ex);
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
