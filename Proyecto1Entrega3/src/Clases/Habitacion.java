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
    
}
