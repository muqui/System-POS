/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.CrearCategoriaDao;
import hibernateUtil.HibernateUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Categoria;
import vista.JInternalFrameCrearCategoria;
import vista.Principal;


/**
 *
 * @author mq12
 */
public class AltaCategoriaControlador implements ActionListener {

    Session session;
    Transaction transaction;
    private Principal principal;
    private JInternalFrameCrearCategoria crearCategoria;
    private Categoria categoria;

    public AltaCategoriaControlador(Principal principal, Categoria categoria, JInternalFrameCrearCategoria crearCategoria) {
        this.principal = principal;
        this.categoria = categoria;
        this.crearCategoria = crearCategoria;
        this.principal.jMenuItemCrearCategoria.addActionListener(this);
        this.crearCategoria.jButtonGuardar.addActionListener(this);
        this.principal.jButtonCrearCategoria.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == principal.jMenuItemCrearCategoria || e.getSource() == principal.jButtonCrearCategoria) {
           mostrar();
        }
        if (e.getSource() == crearCategoria.jButtonGuardar) {
            if (validar() == true) {
                crearCategoria();
            }

            
        }
    }
 public void mostrar() {
        if (crearCategoria.isVisible()) {
            crearCategoria.toFront();
            System.out.println("valor al frente" + crearCategoria.isVisible());

        } else {
            principal.jDesktopPane.add(crearCategoria);
            crearCategoria.pack();
            crearCategoria.setVisible(true);
           
        }
        
    }
    public void crearCategoria() {
        categoria.setNombre(crearCategoria.txtNombre.getText().trim());
        CrearCategoriaDao crearCategoriaDao = new CrearCategoriaDao();
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            crearCategoriaDao.addCategoria(categoria, session);
            crearCategoria.txtNombre.setText("");
            JOptionPane.showMessageDialog(null, "Categoria creada. ", "Exito", JOptionPane.INFORMATION_MESSAGE);

            this.transaction.commit();
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Categoria agregado"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "error", JOptionPane.ERROR_MESSAGE);
            if (this.transaction != null) {
                transaction.rollback();
            }

           // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error campos vacios", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public boolean validar() {
        boolean bandera = true;
        if (crearCategoria.txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Espacio en blanco", "error", JOptionPane.ERROR_MESSAGE);
            bandera = false;
        }
        return bandera;
    }
}
