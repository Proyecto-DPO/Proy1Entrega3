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
            else if(opcion.equals("5")){crearProductoRestaurante();}
            else if(opcion.equals("6")){tarifasSinDefinirProximoAño();}
            else if(opcion.equals("0")){ingresarUsuario();}}
            
        else if(user.getRol().equals("Recepcionista")){
            if(opcion.equals("1")){crearReserva();}
            else if(opcion.equals("2")){GenerarFacturaReserva();}
            else if(opcion.equals("3")){ConsultarInventario();}
            else if(opcion.equals("4")){cancelarReserva();}
            else if(opcion.equals("5")){archivoLog();}
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
            Boolean pagado = Boolean.parseBoolean(input("Ingrese si el pedido ya ha sido pagado (true/false)"));
            servicio.setPagado(pagado);
            reserva.getServiciosConsumidos().add(servicio);
            
        }
        else if(tipoServicio.equals("1")){
            System.out.println(this.hotel.getControladorServicios().mostrarMenu());
            int id = Integer.parseInt(input("Ingrese el numero id del producto del restaurante"));
            ProductoRestaurante servicio = this.hotel.getControladorServicios().getMenuId(id);
            Boolean pagado = Boolean.parseBoolean(input("Ingrese si el pedido ya ha sido pagado (true/false)"));
            servicio.setPagado(pagado);
            reserva.getProductoMenuConsumido().add(servicio);
            
        }
        hotel.cargarServicioConsumido(reserva);
    }
    // Requerimientos Recepcionista
    private void archivoLog() {
        int num = Integer.parseInt(input("Ingrese el número de huespedes para el archivo"));
        ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
        for(int i=0; i < num;i++){
            ArrayList<String> info1 = new ArrayList<String>();
            String nombre = input("Ingrese el nombre del huesped " + (i+1));
            String documento =  input("Ingrese el documento del huesped " + (i+1));
            info1.add(nombre);
            info1.add(documento);

            info.add(info1);
            }
        int id = hotel.archivoLog(info);
        System.out.println("Se generó exitosamente el archivo log con id " + id +".");
    }
    
    public void ConsultarInventario() {
        System.out.println(hotel.ConsultarInventario());
    }
    public void GenerarFacturaReserva() {
        System.out.println(hotel.getControladorReservas().mostrarReservas());
        int id = Integer.parseInt(input("Ingrese el index de la reserva deseada"));
        hotel.GenerarFacturaReserva(hotel.getControladorReservas().getReservaId(id + 1));
        System.out.println("Factura generada existosamente.");
    }
    public void crearReserva() throws ParseException {
        int numHuespedes = Integer.parseInt(input("Ingrese el numero de huespedes de la reserva"));
        int noCama = 0;
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
            if(necesitaCama.equals("false")){noCama++;}
            infoHuesped.add(necesitaCama);
            infoHuespedes.add(infoHuesped);
        }
        String fechaInicial = input("Ingrese la fecha de inicio de la reserva (yyyy-mm-dd)");
        String fechaFinal = input("Ingrese la fecha de finalización de la reserva (yyyy-mm-dd)");
        String idHabitacion = "";
        boolean continuar1 = true;
        boolean continuar2 = true;
        while(continuar1 || continuar2){
        idHabitacion = input("Ingrese el id de la habitación de la reserva");
        Habitacion habitacion = hotel.getControladorHabitaciones().getHabitacion(Integer.parseInt(idHabitacion));
        if(hotel.confirmarDisponibilidad(fechaInicial,fechaFinal,idHabitacion)){
            continuar1 = false;
        }
        else{System.out.println("La habitación no está disponible en esa fecha.");}
        if((numHuespedes-noCama) <= habitacion.getEspacio()){
            continuar2 = false;
        }
        else{
            System.out.println("No hay suficiente espacio en la habitación para el número de huespedes.");
        }}


        int id = this.hotel.crearReserva(infoHuespedes,fechaInicial,fechaFinal,idHabitacion);

        System.out.println("La reserva se creó exitosamente con el id " + id + ".");
        

    }
    private void cancelarReserva() {
        for(int i= 0; i< hotel.getControladorReservas().getReservas().size();i++){
            System.out.println(i + " " + hotel.getControladorReservas().getReservas().get(i).seleccionadorReserva());
        }

        int id = Integer.parseInt(input("Ingrese el id de la reserva a cancelar"));
        boolean bool = hotel.getControladorReservas().cancelarReserva(id);
        if(bool == true){
            System.out.println("La reserva se canceló con exito.");
        }
        else{
            System.out.println("No se puede cancelar la reserva porque faltan menos de 48 horas para su inicio.");
        }
    }
    // Requerimientos Administrador
    public void tarifasSinDefinirProximoAño(){
        System.out.println(this.hotel.getControladorHabitaciones().tarifasSinDefinirProximoAño());
    }
    public void crearProductoRestaurante() {
        String nombre = input("Ingrese el nombre del producto");
        String tipoProducto = input("Ingrese el tipo de producto (comida/bebida)");
        String rangoHoras = input("Ingrese el rango de horas (HH:mm-HH:mm)");
        double precio = Double.parseDouble(input("Ingrese el precio:"));

        this.hotel.getControladorServicios().crearProductoRestaurante(nombre,tipoProducto,rangoHoras,precio);
    } 
    public void cargarTarifaServicio() {
        String decision = input("Determine el precio de un servicio (0) o de un producto del restaurante (1)");
        if(decision.equals("0")){
        System.out.println(this.hotel.getControladorServicios().mostrarServicios());
        int id = Integer.parseInt(input("Seleccione el servicio"));
        Servicio servicio = this.hotel.getControladorServicios().getServicioId(id);
        double nuevaTarifa = Double.parseDouble(input("ingrese el nuevo valor del servicio"));
        servicio.setPrecio(nuevaTarifa);
        hotel.getControladorServicios().cambiarPrecio("Servicios", servicio.getNombreServicio(),nuevaTarifa);}
        else{
            System.out.println(this.hotel.getControladorServicios().mostrarMenu());
            int id = Integer.parseInt(input("Seleccione el producto"));
            ProductoRestaurante producto = this.hotel.getControladorServicios().getMenuId(id);
            double nuevaTarifa = Double.parseDouble(input("ingrese el nuevo valor del producto"));
            producto.setPrecio(nuevaTarifa);
            hotel.getControladorServicios().cambiarPrecio("MenuRestaurante", producto.getNombreServicio(),nuevaTarifa);
        }
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
        this.hotel.cargarArchivoHabitaciones();
        System.out.println("Habitaciones y camas cargados exitosamente");}

    public void mostrarMenu(Usuario user){
        if(user.getRol().equals("Administrador")){
            System.out.println("1. Cargar archivo de habitaciones,camas, tarifas, reservas, huespedes, menu y servicios.");
            System.out.println("2. Crear habitacion en el inventario.");
            System.out.println("3. Cargar tarifa para un tipo de habitación");
            System.out.println("4. Establecer o cambiar tarifa para un servicio.");
            System.out.println("5. Crear producto de restaurante.");
            System.out.println("6. Consultar tarifas sin crear en los proximos 365 días.");
            System.out.println("0. Cerrar.");}

        else if(user.getRol().equals("Recepcionista")){
            System.out.println("1. Crear reserva a nombre de uno o varios huespedes.");
            System.out.println("2. Generar factura para una reserva.");
            System.out.println("3. Consultar inventario de habitaciones.");
            System.out.println("4. Cancelar Reserva.");
            System.out.println("5. Generar archivo log de uno o más huéspedes.");
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

