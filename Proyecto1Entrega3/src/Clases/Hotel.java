package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Hotel {
    private ControladorHabitaciones controladorHabitaciones;
    private ControladorHuespedes controladorHuespedes;
    private ControladorReservas controladorReservas;
    private ControladorServicios controladorServicios;

    public ArrayList<Usuario> usuarios;
    public Hotel(){
        this.usuarios = new ArrayList<Usuario>();
        this.controladorHabitaciones = new ControladorHabitaciones();
        this.controladorHuespedes = new ControladorHuespedes();
        this.controladorReservas = new ControladorReservas();
        this.controladorServicios = new ControladorServicios();
    }
    public void cargarUsuarios() throws IOException{
        File archivo_usuarios = new File("Proyecto1Entrega3/Datos/Usuarios.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(archivo_usuarios))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                Usuario usuario = new Usuario(split[0], split[1], split[2], split[3]);
                this.usuarios.add(usuario);}}}
    public ArrayList<Usuario> getUsuarios(){
        return this.usuarios;}
    public Usuario getUsuario(String user, String contraseña){
        Usuario usuario = null;
        for(int i=0;i<this.usuarios.size();i++){
            if(this.usuarios.get(i).getUsuario().equals(user) && 
                    this.usuarios.get(i).getContraseña().equals(contraseña)){
                 usuario = this.usuarios.get(i);}
                }
        return usuario;}
    public ControladorHabitaciones getControladorHabitaciones(){
        return this.controladorHabitaciones;}
    public ControladorHuespedes getControladorHuespedes(){
        return this.controladorHuespedes;}
    public ControladorReservas getControladorReservas(){
        return this.controladorReservas;}
    public ControladorServicios getControladorServicios(){
        return this.controladorServicios;}

    // Requerimientos Recepcionista
    public String ConsultarInventario() {
        return controladorHabitaciones.consultarInventario();
    }
    public void GenerarFacturaReserva() {
    }
    public int crearReserva(ArrayList<ArrayList<String>> infoHuespedes, String fechaInicial, String fechaFinal, String idHabitacion) throws ParseException {
        Habitacion habitacion = controladorHabitaciones.getHabitacion(Integer.parseInt(idHabitacion));
        Reserva reserva = controladorReservas.crearReserva(infoHuespedes,fechaInicial,fechaFinal,habitacion);

        controladorHabitaciones.getHabitacion(Integer.parseInt(idHabitacion)).getReservas().add(reserva);

        return reserva.getIdReserva();
    }
    // Requerimientos Administrador
    public void cargarMenuRestauranteYServicios(String servicios, String menu) throws IOException {
        controladorServicios.cargarServiciosYMenu(new File(servicios), new File(menu));
    }
    public void cargarTarifaServicio(String tipoHabitacion, double valorTarifa, String fechaInicial, String fechaFinal, String dias) throws ParseException {
        this.controladorHabitaciones.cargarTarifaServicio( tipoHabitacion,  valorTarifa,  fechaInicial,  fechaFinal,  dias);
    }
    public void crearHabitacion(String ubicacion, boolean balcon, boolean vista, boolean cocinaIntegrada, String tipoHabitacion, ArrayList<ArrayList<String>> infoCamas) {
        this.controladorHabitaciones.crearHabitacion(ubicacion,balcon,vista,cocinaIntegrada,tipoHabitacion,infoCamas);
    }
    public void cargarArchivoHabitaciones(String archivoHabitaciones, String archivoCamas) throws NumberFormatException, IOException {
        String rutaHabitaciones = "Proyecto1Entrega3/Datos/" + archivoHabitaciones;
        String rutaCamas = "Proyecto1Entrega3/Datos/" + archivoCamas;
        File ruta_archivoHabitaciones = new File(rutaHabitaciones);
        File ruta_archivoCamas = new File(rutaCamas);
        this.controladorHabitaciones.cargarArchivoHabitaciones(ruta_archivoHabitaciones,ruta_archivoCamas);
        
    }
}
