package Clases;
import java.util.ArrayList;

public class Reserva {

    private RangoFechas rangoFecha;
    private ArrayList<Huesped> huespedes;
    private Habitacion habitacion;
    private int idReserva;
    private ArrayList<ProductoRestaurante> productoMenuConsumido;
    private ArrayList<Servicio> serviciosConsumidos;


    public Reserva(RangoFechas rangoFecha,ArrayList<Huesped> huespedes, Habitacion habitacion, int idReserva){

        this.rangoFecha = rangoFecha;
        this.huespedes = huespedes;
        this.habitacion = habitacion;
        this.idReserva = idReserva;
        this.productoMenuConsumido = new ArrayList<ProductoRestaurante>();
        this.serviciosConsumidos = new ArrayList<Servicio>();
    }

    public Huesped huesped(int id, Huesped huesped){

        return huesped;

    }

    public ArrayList<Huesped> getHuespedes(){

        return this.huespedes;
    }

    public String getRangoFecha() {
        return this.rangoFecha.getFechaInicial().toString() + "-" + this.rangoFecha.getFechaFinal().toString();
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public ArrayList<ProductoRestaurante> getProductoMenuConsumido() {
        return productoMenuConsumido;
    }

    public ArrayList<Servicio> getServiciosConsumidos() {
        return serviciosConsumidos;
    }

}