package Clases;

import java.util.ArrayList;

public class Habitacion {
    private int id;
    private String ubicacion;
    private boolean balcon;
    private boolean vista;
    private boolean cocinaIntegrada;
    private String tipoHabitacion;

    private ArrayList<Cama> camas;
    private ArrayList<Reserva> reservas;

    public Habitacion(int id, String ubicacion, boolean balcon,
        boolean vista, boolean cocinaIntegrada, String tipoHabitacion){
            this.id = id;
            this.ubicacion = ubicacion;
            this.balcon = balcon;
            this.vista  = vista;
            this.cocinaIntegrada = cocinaIntegrada;
            this.tipoHabitacion = tipoHabitacion;
            this.reservas = new ArrayList<Reserva>();
            this.camas = new ArrayList<Cama>();}
    public int getId() {
        return this.id;}
    public boolean isBalcon() {
            return this.balcon;}
    public String getUbicacion() {
            return this.ubicacion;}
    public boolean isVista() {
        return this.vista;
    }
    public boolean isCocinaIntegrada() {
        return this.cocinaIntegrada;
    }
    public String getTipoHabitacion() {
        return this.tipoHabitacion;
    }
    public ArrayList<Cama> getCamas() {
        return this.camas;
    }
    public void addCama(Cama cama){
        this.camas.add(cama);
    }
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    public int getEspacio(){
        int espacio = 0;
        for(int i=0;i<camas.size();i++){
            espacio += camas.get(i).getCantidadPersonas();
        }
        return espacio;
    }
    public String textoInventario(){
        String retorno = ubicacion + " " + "Id->" + this.id + ": Habitación tipo " + this.tipoHabitacion + " con capacidad para " + getEspacio() + " personas.\n";
        retorno += "Cocina Integrada:" + cocinaIntegrada+"\nVista:" + vista + "\nBalcon:" + balcon + "\nCamas:\n";
        for(int i=1;i<=camas.size();i++){
            retorno += "    Cama " + i + ":" + camas.get(i-1).stringFactura() + "\n";
        }
        retorno += "Reservas:\n";
        for(int i=1;i<=reservas.size();i++){
            retorno += reservas.get(i-1).stringInventario() + "\n";
        }
        return retorno;
    }
}
