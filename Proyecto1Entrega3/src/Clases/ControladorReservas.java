package Clases;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControladorReservas {
    
    private ArrayList<Reserva> reservas;
    
    

    public ControladorReservas(){

        this.reservas = new ArrayList<Reserva>();
        

    }

    public Reserva crearReserva(ArrayList<ArrayList<String>> infoHuespedes, String fechaInicial, String fechaFinal, Habitacion habitacion) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        RangoFechas rangoFecha = new RangoFechas(sdf.parse(fechaInicial), sdf.parse(fechaFinal));
        ArrayList<Huesped> huespedes = new ArrayList<Huesped>();
        String huespedesString = "";
        for(int i=0;i<infoHuespedes.size();i++){
            String nombre = infoHuespedes.get(i).get(0);
            int documento = Integer.parseInt(infoHuespedes.get(i).get(1));
            String email = infoHuespedes.get(i).get(2);
            String celular = (infoHuespedes.get(i).get(3));
            boolean necesitaCama = Boolean.parseBoolean(infoHuespedes.get(i).get(4));
            if(i == infoHuespedes.size()-1){
                huespedesString += nombre+":"+documento+":"+email+":"+celular+":"+necesitaCama;
            }
            else{
                huespedesString += nombre+":"+documento+":"+email+":"+celular+":"+necesitaCama+"-";
            }
            Huesped huesped = new Huesped(nombre, documento, email, celular, necesitaCama);
            huespedes.add(huesped);
        }
        int id = reservas.size();
        Reserva reserva = new Reserva(rangoFecha,huespedes,habitacion, id,false);
        try {
            Files.write(Paths.get("Proyecto1Entrega3/Datos/Reservas.txt"),("\n"+id+";"+reserva.getHabitacion().getId()+";"+fechaInicial+";"+fechaFinal+";"+huespedesString+";false").getBytes(), StandardOpenOption.APPEND );
        } catch (IOException e) {
            e.printStackTrace();
        }
        habitacion.getReservas().add(reserva);
        reservas.add(reserva);

        return reserva;
    }
    public String generarFactura(Reserva reserva, ControladorHabitaciones controladorHabitaciones){
        String retorno = "Reserva " + reserva.getIdReserva()  + "\n";
        Habitacion habitacion =  reserva.getHabitacion();
        retorno += "Habitación " + habitacion.getTipoHabitacion() + " para " + habitacion.getEspacio() + " personas.\n";
        retorno += "Rango de fechas " + reserva.getRangoFecha() + ".\n"; 
        retorno += "Precio por la habitacion dadas las tarifas: " + controladorHabitaciones.getPrecioHabitacion(habitacion, reserva.getFechas()) + "\n";
        double totalPagar = controladorHabitaciones.getPrecioHabitacion(habitacion, reserva.getFechas());
        for(int i=0;i<reserva.getHuespedes().size();i++){
            Huesped huesped = reserva.getHuespedes().get(i);
            retorno += "    Huesped " + (i+1) + ":\n";
            retorno += "        Nombre:" + huesped.getNombre()+"\n";
            retorno += "        Documento:" + huesped.getDocumento()+"\n";
            retorno += "        Email:" + huesped.getEmail()+"\n";
            retorno += "        Celular:" + huesped.getCelular()+"\n";}
        retorno += "    Servicios Consumidos:\n";
        for(int i=0;i<reserva.getServiciosConsumidos().size();i++){
            Servicio servicio = reserva.getServiciosConsumidos().get(i);
            if(servicio.isPagado()){
                retorno += "        " + servicio.getNombreServicio() +"*: " + servicio.getPrecio() + "$\n";
            }
            else{
                retorno += "        " + servicio.getNombreServicio() +"*: " + servicio.getPrecio() + "$\n";
                totalPagar += servicio.getPrecio();
            }}
        retorno += "    Productos consumidos del restaurante:\n";
        for(int i=0;i<reserva.getProductoMenuConsumido().size();i++){
            ProductoRestaurante productoRestaurante = reserva.getProductoMenuConsumido().get(i);
            if(productoRestaurante.isPagado()){
                retorno += "        " + productoRestaurante.getNombreServicio() +"*: " + productoRestaurante.getPrecio() + "$\n";
            }
            else{
                retorno += "        " + productoRestaurante.getNombreServicio() +"*: " + productoRestaurante.getPrecio() + "$\n";
                totalPagar += productoRestaurante.getPrecio();
        }}

        retorno += "TOTAL A PAGAR: " + totalPagar + "$\nLos Productos con * ya fueron pagados y no son contados en el precio final.";
        return retorno;
    }

    public Reserva getReservaId(int id){
        return reservas.get(id-1);
    }
    public ArrayList<Reserva> getReservaDocumento(int documento){
        ArrayList<Reserva> reservasDoc = new ArrayList<Reserva>();
        for(int i=0;i<reservas.size();i++){
            for(int r = 0; r < reservas.get(i).getHuespedes().size();r++){
                if(reservas.get(i).getHuespedes().get(r).getDocumento() == documento){
                    reservasDoc.add(reservas.get(i));
                }
            }
        }
        return reservasDoc;}

        public boolean cancelarReserva(int id){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            boolean retorno = false;
            Reserva reserva = getReservaId(id+1);
            Date fechaInicial = reserva.getFechaInicial();
            Date hoy = Calendar.getInstance().getTime();

            if(((fechaInicial.getTime() - hoy.getTime())/3.6e6) >= 48){
                reservas.get(id).setCancelado(true);
                retorno = true;
            }
            if(retorno){
                String huespedesString = "";
                String strServicios = "";
                String strMenu = "";
                ArrayList<Huesped> infoHuespedes = reserva.getHuespedes();
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
                       strServicios += reserva.getServiciosConsumidos().get(i);
                        }
                         else{
                            strServicios += reserva.getServiciosConsumidos().get(i) + "-";
                        }
                }
                for(int  i=0; i<reserva.getProductoMenuConsumido().size();i++){
                    if(i == reserva.getProductoMenuConsumido().size()-1){
                        strMenu += reserva.getProductoMenuConsumido().get(i);
                        }
                         else{
                            strMenu += reserva.getProductoMenuConsumido().get(i) + "-";
                        }
                }
                try {
                    Path path = Paths.get("Proyecto1Entrega3/Datos/Reservas.txt");
                    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    lines.set(id + 1, id + ";" + reserva.getHabitacion().getId() + ";" + sdf.format(fechaInicial) + ";" + sdf.format(reserva.getFechas().getFechaFinal()) + ";" + huespedesString + ";true;" + strServicios +";" + strMenu);

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
            return retorno;
        }

        public ArrayList<Reserva> getReservas(){
            return this.reservas;
        }
    public void cargarReservas(ControladorHabitaciones controladorHabitaciones, ControladorHuespedes controladorHuespedes,
        ControladorServicios controladorServicios) throws IOException, ParseException{
        try (BufferedReader br = new BufferedReader(new FileReader("Proyecto1Entrega3/Datos/Reservas.txt"))) {
            String st;
            br.readLine();
            while ((st = br.readLine()) != null) {
                String[] split = st.split(";");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                RangoFechas rangoFecha = new RangoFechas(sdf.parse(split[2]), sdf.parse(split[3]));
                ArrayList<Huesped> huespedes= new ArrayList<Huesped>();
                String[] split2 = split[4].split("-");
                for(int i=0;i<split2.length;i++){
                    String[] split3 = split2[i].split(":");
                    Huesped huesped = controladorHuespedes.getHuesped(split3[0],Integer.parseInt(split3[1]),split3[2],split3[3], Boolean.parseBoolean(split3[4]));
                    huespedes.add(huesped);
                }
                Habitacion habitacion = controladorHabitaciones.getHabitacion(Integer.parseInt( split[1]));
                Reserva reserva = new Reserva(rangoFecha,huespedes,habitacion,Integer.parseInt(split[0]),Boolean.parseBoolean(split[5]));
                habitacion.getReservas().add(reserva);
                this.reservas.add(reserva);
                for(int i=0;i<huespedes.size();i++){
                    huespedes.get(i).getHistorialReserva().add(reserva);}
                for(int i=0; i<split[6].split("-").length;i++){
                    String[] split1 = split[6].split("-")[i].split(":");
                    Servicio servicio = controladorServicios.getServicioId(Integer.parseInt(split1[0]));
                    servicio.setPagado(Boolean.parseBoolean(split1[1]));
                    reserva.getServiciosConsumidos().add(servicio);}
                for(int i=0; i<split[7].split("-").length;i++){
                    String[] split1 = split[7].split("-")[i].split(":");
                    ProductoRestaurante productoRestaurante = controladorServicios.getMenuId(Integer.parseInt(split1[0]));
                    productoRestaurante.setPagado(Boolean.parseBoolean(split1[1]));
                    reserva.getProductoMenuConsumido().add(productoRestaurante);}}}
    }
    public String mostrarReservas(){
        String st = "";
        for(int i=0;i<reservas.size();i++){
            st += i + ".    "+reservas.get(i).getRangoFecha() + "   id de la reserva:" + reservas.get(i).getIdReserva() + "\n"; 
        }
        return st;
    }
}


