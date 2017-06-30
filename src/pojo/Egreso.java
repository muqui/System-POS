package pojo;
// Generated 2/01/2016 05:27:14 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Egreso generated by hbm2java
 */
public class Egreso  implements java.io.Serializable {


     private Integer id;
     private Usuario usuario;
     private BigDecimal cantidad;
     private String descripcion;
     private Date fechaMovimiento;
     private String nombre;
     private Date fecha;

    public Egreso() {
    }

	
    public Egreso(Usuario usuario, BigDecimal cantidad, Date fechaMovimiento, String nombre, Date fecha) {
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
        this.nombre = nombre;
        this.fecha = fecha;
    }
    public Egreso(Usuario usuario, BigDecimal cantidad, String descripcion, Date fechaMovimiento, String nombre, Date fecha) {
       this.usuario = usuario;
       this.cantidad = cantidad;
       this.descripcion = descripcion;
       this.fechaMovimiento = fechaMovimiento;
       this.nombre = nombre;
       this.fecha = fecha;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public BigDecimal getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFechaMovimiento() {
        return this.fechaMovimiento;
    }
    
    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


