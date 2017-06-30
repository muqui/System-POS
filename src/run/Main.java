/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import controlador.AltaCategoriaControlador;
import controlador.AltaMovimientoControlador;
import controlador.ControladorAltaProducto;
import controlador.CrearUsuarioControlador;
import controlador.EgresoControlador;
import controlador.IngresoControlador;
import controlador.IngresoEgresoControlador;
import controlador.InventarioPapeleriaControlador;
import controlador.LoginControlador;
import controlador.MisVentasControlador;
import controlador.ModificarProductoControlador;
import controlador.ModificarUsuarioControlador;
import controlador.TotalVentasControlador;
import controlador.VenderControlador;
import controlador.VentasControlador;
import controlador.VistaControlador;
import pojo.Categoria;
import pojo.Egreso;
import pojo.Ingreso;
import pojo.Movimiento;
import pojo.Tproducto;
import pojo.Tventadetalle;
import pojo.Usuario;
import vista.EditarEliminarVenta;
import vista.JInternalFrameAltaProducto;
import vista.JInternalFrameAltaUsuario;
import vista.JInternalFrameCrearCategoria;
import vista.JInternalFrameCrearMovimiento;
import vista.JInternalFrameEgreso;
import vista.JInternalFrameIngresEgreso;
import vista.JInternalFrameIngreso;
import vista.JInternalFrameInventarioPapeleria;
import vista.JInternalFrameMisVentas;
import vista.JInternalFrameModificarProducto;
import vista.JInternalFrameModificarUsuario;
import vista.JInternalFrameReporteTotal;
import vista.JInternalFrameVender;
import vista.JInternalFrameVentasDetalles;
import vista.Login;
import vista.Principal;

/**
 *
 * @author mq12
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //modelo
        Usuario usuario = new Usuario();
        Tproducto producto = new Tproducto();
        Categoria categoria = new Categoria();
        Egreso egreso = new Egreso();
        Ingreso ingreso = new Ingreso();
         Movimiento movimiento = new Movimiento();
         Tventadetalle ventaDetalle = new Tventadetalle();
        //Vista
        Principal principal = new Principal();
        JInternalFrameAltaProducto jInternalFrameAltaProducto = new JInternalFrameAltaProducto();
        JInternalFrameVender jInternalFrameVender = new JInternalFrameVender();
        Login login = new Login(principal, true);
        EditarEliminarVenta editarEliminarVenta = new EditarEliminarVenta(principal, true);
        JInternalFrameModificarProducto jInternalFrameModificarProducto = new JInternalFrameModificarProducto();
        JInternalFrameCrearCategoria jInternalFrameCrearCategoria = new JInternalFrameCrearCategoria();
        JInternalFrameEgreso jInternalFrameEgreso = new JInternalFrameEgreso();
        JInternalFrameIngreso jInternalFrameIngreso = new JInternalFrameIngreso();
        JInternalFrameCrearMovimiento jInternalFrameCrearMovimiento = new JInternalFrameCrearMovimiento();
        JInternalFrameMisVentas jInternalFrameMisVentas = new JInternalFrameMisVentas();
        JInternalFrameAltaUsuario jInternalFrameAltaUsuario = new JInternalFrameAltaUsuario();
        JInternalFrameModificarUsuario jInternalFrameModificarUsuario = new JInternalFrameModificarUsuario();
        JInternalFrameReporteTotal jInternalFrameReporteTotal = new JInternalFrameReporteTotal();
        JInternalFrameVentasDetalles jInternalFrameVentasDetalles = new JInternalFrameVentasDetalles();
        JInternalFrameIngresEgreso jInternalFrameIngresEgreso = new JInternalFrameIngresEgreso();
        JInternalFrameInventarioPapeleria jInternalFrameInventarioPapeleria = new JInternalFrameInventarioPapeleria();
        //Controlador
        VistaControlador vistaControlador = new VistaControlador(principal, jInternalFrameAltaProducto);
        LoginControlador loginControlador = new LoginControlador(principal, login, usuario);
        VenderControlador venderControlador = new VenderControlador(principal, jInternalFrameVender, editarEliminarVenta);
        ControladorAltaProducto controladorAltaProducto = new ControladorAltaProducto(principal, usuario, jInternalFrameAltaProducto);
        ModificarProductoControlador modificarProductoControlador = new ModificarProductoControlador(principal, producto, jInternalFrameModificarProducto);
        AltaCategoriaControlador altaCategoriaControlador = new AltaCategoriaControlador(principal, categoria, jInternalFrameCrearCategoria);
        EgresoControlador egresoControlador = new EgresoControlador(principal, egreso, jInternalFrameEgreso);
        IngresoControlador ingresoControlador = new IngresoControlador(principal, ingreso, jInternalFrameIngreso);
        AltaMovimientoControlador altaMovimientoControlador = new AltaMovimientoControlador(principal, movimiento, jInternalFrameCrearMovimiento);
        MisVentasControlador misVentasControlador = new MisVentasControlador(principal, jInternalFrameMisVentas);
        CrearUsuarioControlador crearUsuarioControlador = new CrearUsuarioControlador(principal, usuario, jInternalFrameAltaUsuario);
        ModificarUsuarioControlador modificarUsuarioControlador = new ModificarUsuarioControlador(principal, usuario, jInternalFrameModificarUsuario);
        TotalVentasControlador totalVentasControlador = new TotalVentasControlador(principal, jInternalFrameReporteTotal);
        VentasControlador ventasControlador = new VentasControlador(principal, ventaDetalle, jInternalFrameVentasDetalles);
        IngresoEgresoControlador ingresoEgresoControlador = new IngresoEgresoControlador(principal, jInternalFrameIngresEgreso);
        InventarioPapeleriaControlador inventarioPapeleriaControlador = new InventarioPapeleriaControlador(principal, jInternalFrameInventarioPapeleria);
        
        vistaControlador.iniciar();
        loginControlador.iniciar();
        

    }

}
