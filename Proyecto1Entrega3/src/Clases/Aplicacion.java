package Clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;

public class Aplicacion {
    private Hotel hotel = new Hotel();
    public static void main(String args[]) throws IOException, NumberFormatException, ParseException{
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.ejecutarAplicacion();
    }
    private void ejecutarAplicacion() throws IOException, NumberFormatException, ParseException {
        this.hotel.cargarUsuarios();
        ingresarUsuario();
    }
    public void ingresarUsuario() throws NumberFormatException, IOException, ParseException{
    	boolean continuar1 = true;
        boolean continuar2 = true;
    	while(continuar1 == true) {
	        String usuario = input("Ingrese su usuario");
	        String contraseña = input("Ingrese su contraseña");
	        Usuario user = this.hotel.getUsuario(usuario, contraseña);
	        if(user == null){
	            System.out.println("No se encontró el usuario. Intente de nuevo.");
	        }
	        else{
	        	continuar1 = false;
	            System.out.println("Bienvenido señor/a: " + user.getNombre() + ".\nCargo: " + user.getRol()+".");
                while(continuar2 == true){
                mostrarMenu(user);
                String opcion = input("Ingrese una opción");
                if(opcion.equals("cerrar") == true){
                    continuar2 = false;}
                else{
                    opcionSeleccionada(user,opcion);}
                }
            }}}
    public void opcionSeleccionada(Usuario user, String opcion) throws NumberFormatException, IOException, ParseException {
        if(user.getRol().equals("Administrador")){
            if(opcion.equals("1")){cargarArchivoHabitaciones();}
            else if(opcion.equals("2")){crearHabitacion();}
            else if(opcion.equals("3")){cargarTarifaHabitacion();}
            else if(opcion.equals("4")){cargarTarifaServicio();}
            else if(opcion.equals("5")){cargarMenuRestauranteYServicios();}
            else if(opcion.equals("6")){crearProductoRestaurante();}
            else if(opcion.equals("7")){tarifasSinDefinirProximoAño();}
            else if(opcion.equals("0")){ingresarUsuario();}}
            
        else if(user.getRol().equals("Recepcionista")){
            if(opcion.equals("1")){crearReserva();}
            else if(opcion.equals("2")){GenerarFacturaReserva();}
            else if(opcion.equals("3")){ConsultarInventario();}
            else if(opcion.equals("0")){ingresarUsuario();}}
        else{
            if(opcion.equals("1")){registrarConsumo();}
            else if(opcion.equals("0")){ingresarUsuario();}}
    }
    /// REQUERIMIENTOS

    // Requerimientos Empleado
    public void registrarConsumo() {
        Reserva reserva = null;
        String idODoc = input("Determine la reserva con el id (0) o con el documento de un huesped (1)");
        if (idODoc.equals("0")){
            int id = Integer.parseInt(input("Ingrese el id de la reserva"));
            reserva = this.hotel.getControladorReservas().getReservaId(id+1);}
        else{
            int documento = Integer.parseInt(input("Ingrese el documento de un huesped de la reserva"));
            String reservas = "";
            ArrayList<Reserva> reservasDoc = this.hotel.getControladorReservas().getReservaDocumento(documento);
            for(int i=0;i<reservasDoc.size();i++){
                reservas += i +": Id " + reservasDoc.get(i).getIdReserva() + " Fechas:" + reservasDoc.get(i).getRangoFecha();
            }
            System.out.println(reservas);
            int id = Integer.parseInt(input("Ingrese el id de la reserva deseada"));

            reserva = reservasDoc.get(id);
        }
        String tipoServicio = input("Ingrese si el consumo es de un servicio (0) o de un producto del restaurante (1)");
        if(tipoServicio.equals("0")){
            System.out.println(this.hotel.getControladorServicios().mostrarServicios());
            int id = Integer.parseInt(input("Ingrese el numero id del servicio"));
            Servicio servicio = this.hotel.getControladorServicios().getServicioId(id);
            reserva.getServiciosConsumidos().add(servicio);
            
        }
        else if(tipoServicio.equals("1")){
            System.out.println(this.hotel.getControladorServicios().mostrarMenu());
            int id = Integer.parseInt(input("Ingrese el numero id del producto del restaurante"));
            ProductoRestaurante servicio = this.hotel.getControladorServicios().getMenuId(id);
            reserva.getProductoMenuConsumido().add(servicio);
            
        }
    }
    // Requerimientos Recepcionista
    public void ConsultarInventario() {
    }
    public void GenerarFacturaReserva() {
    }
    public void crearReserva() throws ParseException {
        int numHuespedes = Integer.parseInt(input("Ingrese el numero de huespedes de la reserva"));
        ArrayList<ArrayList<String>> infoHuespedes = new ArrayList<ArrayList<String>>();
        for(int i=1;i <= numHuespedes;i++){
            ArrayList<String> infoHuesped = new ArrayList<String>();
            String nombre = input("Ingrese el nombre del huesped " + i); 
            infoHuesped.add(nombre);
            String documento = input("Ingrese el numero de documento del huesped " + i); 
            infoHuesped.add(documento);
            String email = input("Ingrese el email del huesped " + i); 
            infoHuesped.add(email); 
            String celular = input("Ingrese el numero de celular del huesped " + i); 
            infoHuesped.add(celular); 
            String necesitaCama = input("Ingrese si el huesped " + i + " necesita cama (true/false)");
            infoHuesped.add(necesitaCama);
            infoHuespedes.add(infoHuesped);
        }
        String fechaInicial = input("Ingrese la fecha de inicio de la reserva (yyyy-mm-dd)");
        String fechaFinal = input("Ingrese la fecha de finalización de la reserva (yyyy-mm-dd)");
        String idHabitacion = input("Ingrese el id de la habitación de la reserva");

        int id = this.hotel.crearReserva(infoHuespedes,fechaInicial,fechaFinal,idHabitacion);

        System.out.println("La reserva se creó exitosamente con el id " + id + ".");
        

    }
    // Requerimientos Administrador
    public void tarifasSinDefinirProximoAño(){
        System.out.println(this.hotel.getControladorHabitaciones().tarifasSinDefinirProximoAño());
    }
    public void crearProductoRestaurante() {
    }
    public void cargarMenuRestauranteYServicios() throws IOException {
        String servicios = input("Ingrese el nombre del archivo de servicios");
        String menu = input("Ingrese el nombre del archivo de menu");

        this.hotel.cargarMenuRestauranteYServicios("Proyecto1Entrega3/Datos/"+servicios,"Proyecto1Entrega3/Datos/"+menu);
    }
    public void cargarTarifaServicio() {
    }
    public void cargarTarifaHabitacion() throws ParseException {
        String tipoHabitacion = input("Ingrese el tipo de habitación para aplicar la tarifa (estandar, suite, o suite doble)");
        double valorTarifa = Double.parseDouble(input("Ingrese el valor de la tarifa"));
        String fechaInicial = input("Ingrese la fecha inicial (formato yyyy-mm-dd)");
        String fechaFinal = input("Ingrese la fecha final (formato yyyy-mm-dd)");
        String dias = input("Ingrese los días en los que aplica la tarifa (L:Lunes,M:Martes,X:Miecoles,J:Jueves,V:Viernes,S:Sabado,D:Domingo), ej: LMS");
        this.hotel.cargarTarifaServicio(tipoHabitacion,valorTarifa,fechaInicial,fechaFinal,dias);
    }
    public void crearHabitacion() {
        String ubicacion = input("Ingrese la ubicación de la habitacion");
        String balcon = input("Ingrese si tiene balcon (true/false)");
        String vista = input("Ingrese si tiene vista (true/false)");
        String cocinaIntegrada = input("Ingrese si tiene cocina integrada (true/false)");
        String tipoHabitacion = input("Ingrese el tipo de habitación (estandar, suite, o suite doble)");
        String numCamas = input("Ingrese el número de camas que tiene la habitación (minimo 1)");
        ArrayList<ArrayList<String>> infoCamas = new ArrayList<ArrayList<String>>();
        for(int i=1; i <= Integer.parseInt(numCamas);i++){
            ArrayList<String> datosCama = new ArrayList<String>();
            String tamaño = input("Ingrese el tamaño (individual, doble, king o queen) de la cama " + i);
            String cantidadPersonas = input("Ingrese la cantidad de personas de la cama " + i);
            String soloNiños = input("Ingrese si es solo para niños (true/false) para la cama " + i);
            datosCama.add(tamaño);
            datosCama.add(cantidadPersonas);
            datosCama.add(soloNiños);
            infoCamas.add(datosCama);}
        this.hotel.crearHabitacion(ubicacion, Boolean.parseBoolean(balcon), Boolean.parseBoolean(vista),
        Boolean.parseBoolean(cocinaIntegrada), tipoHabitacion, infoCamas);}
    public void cargarArchivoHabitaciones() throws NumberFormatException, IOException {
        String archivoHabitaciones = input("Ingrese el nombre del archivo de habitaciones");
        String archivoCamas = input("Ingrese el nombre del archivo de camas");
        this.hotel.cargarArchivoHabitaciones(archivoHabitaciones,archivoCamas);}

    ///
    public void mostrarMenu(Usuario user){
        if(user.getRol().equals("Administrador")){
            System.out.println("1. Cargar archivo habitaciones.");
            System.out.println("2. Crear habitacion en el inventario.");
            System.out.println("3. Cargar tarifa para un tipo de habitación");
            System.out.println("4. Establecer o cambiar tarifa para un servicio.");
            System.out.println("5. Cargar menú restaurante y catalogo de servicios.");
            System.out.println("6. Crear producto de restaurante.");
            System.out.println("7. Consultar tarifas sin crear en los proximos 365 días.");
            System.out.println("0. Cerrar.");}

        else if(user.getRol().equals("Recepcionista")){
            System.out.println("1. Crear reserva a nombre de uno o varios huespedes.");
            System.out.println("2. Generar factura para una reserva.");
            System.out.println("3. Consultar inventario de habitaciones.");
            System.out.println("0. Cerrar.");}
        
        else if(user.getRol().equals("Empleado")){
            System.out.println("1. Registrar el consumo de una habitación.");
            System.out.println("0. Cerrar.");}
        }

    public String input(String mensaje){try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;}

}

