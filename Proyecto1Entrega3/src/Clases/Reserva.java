package Clases;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return  sdf.format(this.rangoFecha.getFechaInicial()) + " - " + sdf.format(this.rangoFecha.getFechaFinal());
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
    public String stringInventario(){
        String retorno ="   "+ getRangoFecha() + ":\n";
        for(int i=0; i<huespedes.size();i++){
            retorno += "        " + huespedes.get(i).getNombre() + " - " + huespedes.get(i).getDocumento() + "\n";
        }
        return retorno;
    }
    public String seleccionadorReserva(){
        String retorno = getRangoFecha() + ":";
        for(int i=0; i<huespedes.size();i++){
            retorno += huespedes.get(i).getNombre() + " - " + huespedes.get(i).getDocumento() + "\n";
        }
        return retorno;
    }
    public Date getFechaInicial(){
        return this.rangoFecha.getFechaInicial();
    }
}   