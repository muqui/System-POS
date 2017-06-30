/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.CrearUsuarioDao;
import encryption.Encryption;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Usuario;
import vista.JInternalFrameAltaUsuario;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class CrearUsuarioControlador implements ActionListener {

    private Principal principal;
    private JInternalFrameAltaUsuario crearUsuarioGui;
    private Usuario usuario;
    CrearUsuarioDao crearUsuarioDao;
    Session session;
    Transaction transaction;

    public CrearUsuarioControlador(Principal principal, Usuario usuario, JInternalFrameAltaUsuario crearUsuarioGui) {
        this.principal = principal;
        this.usuario = usuario;
        this.crearUsuarioGui = crearUsuarioGui;
        crearUsuarioDao = new CrearUsuarioDao();
        this.principal.jMenuItemCrearUsuario.addActionListener(this);
        this.crearUsuarioGui.jButtonGuardar.addActionListener(this);
        this.principal.jButtonCrearUsuario.addActionListener(this);
    }

    public void crearUsuario() {
        String password = crearUsuarioGui.txtContraseña.getText();
        String confirmar = crearUsuarioGui.txtConfirmar.getText();
        usuario.setNivel(Integer.parseInt("" + crearUsuarioGui.jComboBoxTipoMovimiento.getSelectedItem()));
        usuario.setNombre(crearUsuarioGui.txtNombre.getText().trim());
        usuario.setPassword(crearUsuarioGui.txtContraseña.getText());
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            if (password.equals(confirmar)) {
//            if (usuario.getPassword().equals(this.confirmarPassword)) {
                usuario.setPassword(Encryption.encrypt(usuario.getPassword()));

                crearUsuarioDao.altaProducto(usuario, session);
                 JOptionPane.showMessageDialog(null, "Usuario Registrado", "Exito", JOptionPane.INFORMATION_MESSAGE);
                 crearUsuarioGui.txtConfirmar.setText("");
                 crearUsuarioGui.txtContraseña.setText("");
                 crearUsuarioGui.txtNombre.setText("");
                this.transaction.commit();
               // this.session.close();

            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas con coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }

        } finally {
            if (this.session != null) {
                this.session.close();

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemCrearUsuario || e.getSource() == principal.jButtonCrearUsuario) {
           mostrar();
        }
        if (e.getSource() == crearUsuarioGui.jButtonGuardar) {
            if (validar() == true) {
                crearUsuario();
            }

        }
    }
 public void mostrar() {
        if (crearUsuarioGui.isVisible()) {
            crearUsuarioGui.toFront();
         

        } else {
            principal.jDesktopPane.add(crearUsuarioGui);
            crearUsuarioGui.pack();
            crearUsuarioGui.setVisible(true);
           
        }
        
    }
    public boolean validar() {
        boolean bandera = true;
        if (crearUsuarioGui.txtNombre.getText().trim().equals("") || crearUsuarioGui.txtContraseña.getText().trim().equals("") || crearUsuarioGui.txtConfirmar.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "No se admiten campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
            bandera = false;
        } 
        return bandera;
    }

}
