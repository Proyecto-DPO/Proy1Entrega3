package Clases;
import java.util.ArrayList;

public class Huesped {

    private String nombre;
    private int documento; 
    private String email; 
    private int celular; 
    private boolean necesitaCama; 
    private ArrayList<Reserva> historialReserva;

    public Huesped(String nombre, int documento, String email,int celular,boolean necesitaCama){

        nombre = this.nombre;
        documento = this.documento;
        email = this.email;
        celular = this.celular;
        necesitaCama = this.necesitaCama;
    }

    public ArrayList<Reserva> mostrarHistorialReservas(){

        return historialReserva;
    }

    public ArrayList<String> getInfo(){

        ArrayList<String> info = new ArrayList<>();
        info.add(nombre);
        info.add(Integer.toString(documento));
        info.add(email);
        info.add(Integer.toString(celular));
        info.add(Boolean.toString(necesitaCama));

        return info;


    }



    
}
