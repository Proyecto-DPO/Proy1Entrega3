package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hotel {

    private boolean habitacionesCargadas;

    private ControladorHabitaciones controladorHabitaciones;
    private ControladorHuespedes controladorHuespedes;
    private ControladorReservas controladorReservas;
    private ControladorServicios controladorServicios;

    public ArrayList<Usuario> usuarios;
    public Hotel(){
        this.habitacionesCargadas = false;
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
    public int archivoLog(ArrayList<ArrayList<String>> info) {
        ArrayList<Huesped> huespedes = new ArrayList<Huesped>();
        int id = 0;
        for(int i=0;i<info.size();i++){
            int documento = Integer.parseInt(info.get(i).get(1));
            huespedes.add(controladorHuespedes.getHuesped(info.get(i).get(0),documento, "-", "-", false));
            id += documento;
            }
        File rutaFactura = new File("Proyecto1Entrega3/Logs/" + id +".txt");
        try {
            rutaFactura.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer;
        try {
            writer = new FileWriter(rutaFactura);
            writer.write(controladorHuespedes.generarArchivoLog(huespedes));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
    public String ConsultarInventario() {
        return controladorHabitaciones.consultarInventario();
    }
    public void GenerarFacturaReserva(Reserva reserva) {
        File rutaFactura = new File("Proyecto1Entrega3/Facturas/" + Integer.toString(reserva.getIdReserva())+".txt");
		try {
            rutaFactura.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
		FileWriter writer;
        try {
            writer = new FileWriter(rutaFactura);
            writer.write(controladorReservas.generarFactura(reserva, controladorHabitaciones));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int crearReserva(ArrayList<ArrayList<String>> infoHuespedes, String fechaInicial, String fechaFinal, String idHabitacion) throws ParseException {
        Habitacion habitacion = controladorHabitaciones.getHabitacion(Integer.parseInt(idHabitacion));
        Reserva reserva = controladorReservas.crearReserva(infoHuespedes,fechaInicial,fechaFinal,habitacion);
        ArrayList<Huesped> huespedes = reserva.getHuespedes();
        for(int i=0;i<huespedes.size();i++){
            String nombre = huespedes.get(i).getNombre();
            int documento = huespedes.get(i).getDocumento();
            String email = huespedes.get(i).getEmail();
            String celular = huespedes.get(i).getCelular();
            boolean necesitaCama = huespedes.get(i).isNecesitaCama();

            Huesped huesped = controladorHuespedes.getHuesped(nombre, documento, email, celular, necesitaCama);
            huesped.getHistorialReserva().add(reserva);
        }
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
    public void cargarArchivoHabitaciones() throws NumberFormatException, IOException {
        try {
            controladorHabitaciones.cargarTarifas();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cargarMenuRestauranteYServicios("Proyecto1Entrega3/Datos/Servicios.txt","Proyecto1Entrega3/Datos/MenuRestaurante.txt");
        controladorHuespedes.cargarHuespedes();
        String rutaHabitaciones = "Proyecto1Entrega3/Datos/Habitaciones.txt";
        String rutaCamas = "Proyecto1Entrega3/Datos/Camas.txt";
        File ruta_archivoHabitaciones = new File(rutaHabitaciones);
        File ruta_archivoCamas = new File(rutaCamas);
        this.controladorHabitaciones.cargarArchivoHabitaciones(ruta_archivoHabitaciones,ruta_archivoCamas);
        try {
            controladorReservas.cargarReservas(controladorHabitaciones,controladorHuespedes,controladorServicios);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        habitacionesCargadas = true;
    }
    public boolean isHabitacionesCargadas() {
        return habitacionesCargadas;
    }
    public boolean confirmarDisponibilidad(String fechaInicial, String fechaFinal, String idHabitacion) {
        boolean disponible = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Habitacion habitacion = controladorHabitaciones.getHabitacion(Integer.parseInt(idHabitacion));
        try {
            Date dateInicial = sdf.parse(fechaInicial);
            Date dateFinal = sdf.parse(fechaFinal);
            RangoFechas rangoFechas = new RangoFechas(dateInicial, dateFinal);
            Calendar calInicial = Calendar.getInstance();
            Calendar calFinal = Calendar.getInstance();
            calInicial.setTime(dateInicial);
            calFinal.setTime(dateFinal);
            while(rangoFechas.fechaEnRango(calInicial.getTime())){
                for(int i=0;i<habitacion.getReservas().size();i++){
                    Reserva reserva = habitacion.getReservas().get(i);
                    if(reserva.getFechas().fechaEnRango(calInicial.getTime()) && reserva.isCancelado() == false){
                        disponible = false;
                    }
                }
                calInicial.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return disponible;
    }
    public void cargarServicioConsumido(Reserva reserva) { 
        String strServicios = "-";
        String strMenu = "-";
        String huespedesString = "";
        ArrayList<Huesped> infoHuespedes = reserva.getHuespedes();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicial = reserva.getFechaInicial();
        
        for(int i=0;i<infoHuespedes.size();i++){
            String nombre = infoHuespedes.get(i).getNombre();
            int documento = infoHuespedes.get(i).getDocumento();
            String email = infoHuespedes.get(i).getEmail();
            String celular = infoHuespedes.get(i).getCelular();
            boolean necesitaCama = infoHuespedes.get(i).isNecesitaCama();
            if(i == infoHuespedes.size()-1){
            huespedesString += nombre+":"+documento+":"+email+":"+celular+":"+necesitaCama;
            }
             else{
            huespedesString += nombre+":"+documento+":"+email+":"+celular+":"+necesitaCama+"-";
            }}
        for(int  i=0; i<reserva.getServiciosConsumidos().size();i++){
            if(i == reserva.getServiciosConsumidos().size()-1){
               strServicios += controladorServicios.getIdServicio(reserva.getServiciosConsumidos().get(i).getNombreServicio()) +":" +reserva.getServiciosConsumidos().get(i).isPagado() ;
                }
                 else{
                    strServicios += controladorServicios.getIdServicio(reserva.getServiciosConsumidos().get(i).getNombreServicio()) +":" +reserva.getServiciosConsumidos().get(i).isPagado() + "-";
                }
        }
        for(int  i=0; i<reserva.getProductoMenuConsumido().size();i++){
            if(i == reserva.getProductoMenuConsumido().size()-1){
                strMenu += controladorServicios.getIdMenu(reserva.getProductoMenuConsumido().get(i).getNombreServicio()) + ":" +reserva.getProductoMenuConsumido().get(i).isPagado();
                }
                 else{
                    strMenu += controladorServicios.getIdMenu(reserva.getProductoMenuConsumido().get(i).getNombreServicio()) + ":" +reserva.getProductoMenuConsumido().get(i).isPagado() + "-";
                }
        }

        try {
            Path path = Paths.get("Proyecto1Entrega3/Datos/Reservas.txt");
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.set(reserva.getIdReserva() + 1, reserva.getIdReserva() + ";" + reserva.getHabitacion().getId() + ";" + sdf.format(fechaInicial) + ";" + sdf.format(reserva.getFechas().getFechaFinal()) + ";" + huespedesString + ";"+reserva.isCancelado()+";" + strServicios +";" + strMenu);

            try (OutputStream out = new FileOutputStream(path.toFile())) {
                int lastIndex = lines.size() - 1;
                for (int a = 0; a < lines.size(); a++) {
                    String line = lines.get(a);
                    out.write(line.getBytes(StandardCharsets.UTF_8));
                    if (a != lastIndex) {
                        out.write('\n');}
}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}   
