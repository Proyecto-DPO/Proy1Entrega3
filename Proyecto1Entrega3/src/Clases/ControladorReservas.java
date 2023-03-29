package Clases;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
                huespedesString += nombre+":"+documento+":"+email+":"+celular+":"+necesitaCama+"-";
            }
            else{
                huespedesString += nombre+":"+documento;
            }
            Huesped huesped = new Huesped(nombre, documento, email, celular, necesitaCama);
            huespedes.add(huesped);
        }
        int id = reservas.size();
        Reserva reserva = new Reserva(rangoFecha,huespedes,habitacion, id);
        //id;idHabitacion;fechaInicial;fechaFinal;huespedes
        try {
            Files.write(Paths.get("Proyecto1Entrega3/Datos/Reservas.txt"),("\n"+id+";"+reserva.getHabitacion().getId()+";"+fechaInicial+";"+fechaFinal+";"+huespedesString).getBytes(), StandardOpenOption.APPEND );
        } catch (IOException e) {
            e.printStackTrace();
        }

        reservas.add(reserva);

        return reserva;
    }
    public String generarFactura(){

        String factura = "";
        int len = reservas.size();

        for(int i = 0;i<len;i++){


            Reserva reserva = reservas.get(i);
            ArrayList<Huesped> huespedes = reserva.getHuespedes();
            int len2 = huespedes.size();

            for(int j = 0;j<len2;j++){

                Huesped huesped = huespedes.get(j);
                ArrayList<String> huespedInfo = huesped.getInfo();

                int len3 = huespedInfo.size();

                for(int z = 0;z<len3;z++){

                    String line = huespedInfo.get(z);
                    factura += line; 
                    factura += "\n";

                    
                }
                
            }

        }

        //Falta inlcuir la información de los servicios utilizados por el huesped

        return factura;
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
            boolean retorno = false;
            Reserva reserva = getReservaId(id+1);
            Date fechaInicial = reserva.getFechaInicial();
            Date hoy = Calendar.getInstance().getTime();

            if(((fechaInicial.getTime() - hoy.getTime())/3.6e6) >= 48){
                reservas.remove(id);
                retorno = true;
            }
            return retorno;
        }

        public ArrayList<Reserva> getReservas(){
            return this.reservas;
        }
    public void cargarReservas(ControladorHabitaciones controladorHabitaciones) throws IOException, ParseException{
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
                    huespedes.add(new Huesped(split3[0],Integer.parseInt(split3[1]),split3[2],split3[3], Boolean.parseBoolean(split3[4])));
                }
                Reserva reserva = new Reserva(rangoFecha,huespedes,controladorHabitaciones.getHabitacion(Integer.parseInt( split[1])),Integer.parseInt(split[0]));
                this.reservas.add(reserva);}}
    }
}


