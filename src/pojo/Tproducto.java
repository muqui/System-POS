package pojo;
// Generated 2/01/2016 05:27:14 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Tproducto generated by hbm2java
 */
public class Tproducto  implements java.io.Serializable {


     private Integer idProducto;
     private Categoria categoria;
     private String codigoBarras;
     private String nombre;
     private BigDecimal precioVentaUnitario;
     private int cantidad;
     private String descripcion;
     private BigDecimal precioProveedor;
     private Set<Tventadetalle> tventadetalles = new HashSet<Tventadetalle>(0);

    public Tproducto() {
    }

	
    public Tproducto(Categoria categoria, String codigoBarras, String nombre, BigDecimal precioVentaUnitario, int cantidad, String descripcion, BigDecimal precioProveedor) {
        this.categoria = categoria;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precioVentaUnitario = precioVentaUnitario;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioProveedor = precioProveedor;
    }
    public Tproducto(Categoria categoria, String codigoBarras, String nombre, BigDecimal precioVentaUnitario, int cantidad, String descripcion, BigDecimal precioProveedor, Set<Tventadetalle> tventadetalles) {
       this.categoria = categoria;
       this.codigoBarras = codigoBarras;
       this.nombre = nombre;
       this.precioVentaUnitario = precioVentaUnitario;
       this.cantidad = cantidad;
       this.descripcion = descripcion;
       this.precioProveedor = precioProveedor;
       this.tventadetalles = tventadetalles;
    }
   
    public Integer getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }
    public Categoria getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public String getCodigoBarras() {
        return this.codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public BigDecimal getPrecioVentaUnitario() {
        return this.precioVentaUnitario;
    }
    
    public void setPrecioVentaUnitario(BigDecimal precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecioProveedor() {
        return this.precioProveedor;
    }
    
    public void setPrecioProveedor(BigDecimal precioProveedor) {
        this.precioProveedor = precioProveedor;
    }
    public Set<Tventadetalle> getTventadetalles() {
        return this.tventadetalles;
    }
    
    public void setTventadetalles(Set<Tventadetalle> tventadetalles) {
        this.tventadetalles = tventadetalles;
    }




}


