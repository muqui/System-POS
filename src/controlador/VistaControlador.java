/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.JInternalFrameAltaProducto;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class VistaControlador implements ActionListener {
 Principal principal; 
        JInternalFrameAltaProducto jInternalFrameAltaProducto; 
    public VistaControlador(Principal principal, JInternalFrameAltaProducto jInternalFrameAltaProducto) {
       this.jInternalFrameAltaProducto = jInternalFrameAltaProducto;
       this.principal = principal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
    }
   public void iniciar(){
       principal.setVisible(true);
   } 
}
