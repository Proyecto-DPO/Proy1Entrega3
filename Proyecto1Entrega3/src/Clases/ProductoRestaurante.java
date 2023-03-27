package Clases;

public class ProductoRestaurante extends Servicio{

    public String rangoHoras;
    public String tipoProducto;
    public ProductoRestaurante(String nombreServicio, String tipoCobro, String lugarServicio,
            double precio, String rangoHoras, String tipoProducto) {
        super(nombreServicio, tipoCobro, lugarServicio, precio);
        this.rangoHoras = rangoHoras;
        this.tipoProducto = tipoProducto;
    }


}
