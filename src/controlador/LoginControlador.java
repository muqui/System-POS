/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.LoginDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pojo.Usuario;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class LoginControlador implements ActionListener {

    Principal principal;
    Login login;
    Usuario usuario;

    public LoginControlador(Principal principal, Login login, Usuario usuario) {
        this.principal = principal;
        this.login = login;
        this.usuario = usuario;
        login.btnIngresar.addActionListener(this);

        principal.jMenuItemSalir.addActionListener(this);
        principal.jMenuItemCerrarSeccion.addActionListener(this);
        principal.jButtonSalor.addActionListener(this);
        principal.jButtonCerrarSecion.addActionListener(this);
    }

    public void iniciar() {
        try {
            LoginDao loginDao = new LoginDao();
            loginDao.verificarDatos("", "");
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(LoginControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login() {
        login.btnIngresar.setVisible(false);
        try {
            String user = login.getTxtUsuario().getText();
            String contraseña = encryption.Encryption.encrypt(login.getTxtContraseña().getText());

            System.out.println("Has precionado el boton " + contraseña);
//            UsuarioDao usuarioDao = new UsuarioDao();
//            usuario = usuarioDao.verificarDatos(user, contraseña);
            LoginDao loginDao = new LoginDao();
            usuario = loginDao.verificarDatos(user, contraseña);
            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto ", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                principal.usuario = this.usuario;
                System.out.println("nombre " + usuario.getNombre() + " password " + usuario.getPassword() + " nivel " + usuario.getNivel());
                if (usuario.getId() == 1) {
                    principal.jMenuReportes.setVisible(true);
                    principal.jMenuAdministrador.setVisible(true);
                    principal.jMenuMovimiento.setVisible(true);

                    principal.jButtonVentas.setVisible(true);
                    principal.jButtonTotal.setVisible(true);
                    principal.jButtonEgreso.setVisible(true);
                    principal.jButtonIngreso.setVisible(true);
                    principal.jButtonCrearCategoria.setVisible(true);
                    principal.jButtonCrearUsuario.setVisible(true);
                    principal.jButtonModificarUsuario.setVisible(true);
                    principal.jButtonIngresoEgreso.setVisible(true);
                    principal.jButtonInventario.setVisible(true);
                } else {
                    principal.jMenuReportes.setVisible(false);
                    principal.jMenuAdministrador.setVisible(false);
                    principal.jMenuMovimiento.setVisible(false);

                    principal.jButtonVentas.setVisible(false);
                    principal.jButtonTotal.setVisible(false);
                    principal.jButtonEgreso.setVisible(false);
                    principal.jButtonIngreso.setVisible(false);
                    principal.jButtonCrearCategoria.setVisible(false);
                    principal.jButtonCrearUsuario.setVisible(false);
                    principal.jButtonModificarUsuario.setVisible(false);
                    principal.jButtonIngresoEgreso.setVisible(false);
                    principal.jButtonInventario.setVisible(false);
                }
                login.setVisible(false);
                principal.setTitle("muquiVentas2016-"+usuario.getNombre());
                
                //principal.setUsuario(usuario);
                activar();
            }
            login.txtContraseña.setText("");
            login.txtUsuario.setText("");
        } catch (Exception ex) {

        }
        login.btnIngresar.setVisible(true);
    }

    public void activar() {

        principal.jButtonAltaProducto.setEnabled(true);
        principal.jButtonCerrarSecion.setEnabled(true);
        principal.jButtonCrearCategoria.setEnabled(true);
        principal.jButtonCrearMovimiento.setEnabled(true);
        principal.jButtonCrearUsuario.setEnabled(true);
        principal.jButtonEgreso.setEnabled(true);
        principal.jButtonIngreso.setEnabled(true);
        principal.jButtonMisVEntas.setEnabled(true);
        principal.jButtonModificarProducto.setEnabled(true);
        principal.jButtonModificarUsuario.setEnabled(true);
        principal.jButtonSalor.setEnabled(true);
        principal.jButtonTotal.setEnabled(true);
        principal.jButtonVentas.setEnabled(true);
        principal.jButtonIngresoEgreso.setEnabled(true);
        principal.jMenu1.setEnabled(true);
        principal.jMenu2.setEnabled(true);
        principal.jMenuAdministrador.setEnabled(true);
        principal.jMenuMisVentas.setEnabled(true);
        principal.jMenuReportes.setEnabled(true);
        principal.jMenuMovimiento.setEnabled(true);
        principal.jButtonInventario.setEnabled(true);
        principal.jButtonVender.setEnabled(true);
//        principal.txtBuscar.setEditable(true);
//        principal.jBVender.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login.btnIngresar) {
            login();
        }
        if (e.getSource() == principal.jMenuItemCerrarSeccion || e.getSource() == principal.jButtonCerrarSecion) {
             principal.jDesktopPane.removeAll();
                principal.jDesktopPane.updateUI();
             login.setVisible(true);
           
               
                System.out.println("remover todo");
           
           
        }
        if (e.getSource() == principal.jMenuItemSalir || e.getSource() == principal.jButtonSalor) {
            System.exit(0);
        }
    }
}
